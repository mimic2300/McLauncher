package cz.mimic.mclauncher.alias;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.annotations.SerializedName;

import cz.mimic.mclauncher.util.Message;

public final class AliasBuilder
{
    private static final Map<Class<?>, Object> CLASSES = new HashMap<Class<?>, Object>();
    private static final Map<String, Object> ALIASES = new HashMap<String, Object>();

    private static final Pattern PATTERN = Pattern.compile("(\\{)(.+?)(\\})");
    private static final Pattern PATTERN_SYSTEM_VAR = Pattern.compile("(%)(.+?)(%)");

    private AliasBuilder()
    {}

    public static void addClass(Class<?> clazz, Object classInstance)
    {
        if (!CLASSES.containsKey(clazz)) {
            CLASSES.put(clazz, classInstance);
        }
    }

    public static void rebuild()
    {
        ALIASES.clear();

        for (Class<?> clazz : CLASSES.keySet()) {
            addAliasesFromClass(clazz, CLASSES.get(clazz));
            translateClass(clazz, CLASSES.get(clazz));
        }
    }

    public static Object get(String alias)
    {
        return (alias == null) ? alias : ALIASES.get(alias.toUpperCase());
    }

    public static String translateText(String inputText)
    {
        if (inputText == null) {
            return inputText;
        }

        final Matcher matcher = PATTERN.matcher(inputText);
        while (matcher.find()) {
            String alias = matcher.group(2);
            inputText = inputText.replace(matcher.group(1) + alias + matcher.group(3), get(alias).toString());
        }

        final Matcher matcherSysVar = PATTERN_SYSTEM_VAR.matcher(inputText);
        while (matcherSysVar.find()) {
            String alias = matcherSysVar.group(2);
            String env = System.getenv(matcherSysVar.group(2));

            if (env != null) {
                inputText = inputText.replace(matcherSysVar.group(1) + alias + matcherSysVar.group(3), env);
            }
        }
        return inputText;
    }

    private static void addAliasesFromClass(Class<?> clazz, Object classInstance)
    {
        final Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.isAnnotationPresent(IgnoreAlias.class)) {
                continue;
            }
            if (field.isAnnotationPresent(SerializedName.class)) {
                SerializedName annon = field.getAnnotation(SerializedName.class);
                String alias = annon.value().toUpperCase();

                try {
                    if (ALIASES.containsKey(alias)) {
                        Message.warning(String.format("Alias: {%s} -> {%s::%s} already exists!",
                                alias,
                                clazz.getName(),
                                field.getName()));
                    } else {
                        ALIASES.put(alias, field.get(classInstance));
                    }
                } catch (IllegalArgumentException e) {
                    Message.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    Message.error(e.getMessage());
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked" })
    private static void translateClass(Class<?> clazz, Object classInstance)
    {
        final Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.isAnnotationPresent(IgnoreAlias.class)) {
                continue;
            }
            if (field.isAnnotationPresent(SerializedName.class)) {
                try {
                    Object value = field.get(classInstance);

                    if (value != null) {
                        if (value instanceof List) {
                            List<String> list = (List<String>) value;

                            for (int i = 0; i < list.size(); i++) {
                                String translate = translateText(list.get(i));
                                list.set(i, translate);
                            }
                        } else if (value instanceof Map) {
                            Map<String, String> map = (Map<String, String>) value;

                            for (Entry<String, String> entry : map.entrySet()) {
                                String translate = translateText(entry.getValue().toString());
                                map.remove(entry.getKey());
                                map.put(entry.getKey(), translate);
                            }
                        } else {
                            String translate = translateText(value.toString());
                            field.set(classInstance, translate);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    Message.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    Message.error(e.getMessage());
                }
            }
        }
    }
}
