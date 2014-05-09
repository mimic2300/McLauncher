package cz.mimic.mclauncher.tag;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.logger.Logger;

/**
 * Sestavuje seznam vsech dostupnych tagu ze trid, ktere jsou oznaceny jako tagged a nahrazuje tagy v konfiguracnim
 * souboru za hodnoty.
 * 
 * @author mimic
 */
public final class TagBuilder
{
    private static final Logger LOGGER = new Logger(TagBuilder.class);

    private static final Pattern PATTERN_TAG = Pattern.compile("(\\{)(.+?)(\\})");
    private static final Pattern PATTERN_SYSTEM_VAR = Pattern.compile("(%)(.+?)(%)");

    private static final Map<Class<?>, Object> CLASSES = new HashMap<Class<?>, Object>();
    private static final Map<String, Object> TAGS = new HashMap<String, Object>();

    private TagBuilder()
    {}

    /**
     * Prida tridu do seznamu trid.
     * 
     * @param clazz
     * @param clazzInstance
     */
    public static void addClass(Class<?> clazz, Object clazzInstance)
    {
        if (clazz == null || clazzInstance == null) {
            LOGGER.error("addClass", "Nejaky parametr je NULL");
            return;
        }
        if (!clazzInstance.getClass().equals(clazz)) {
            LOGGER.error("addClass", "Instance tridy %s se neshoduje s typem tridy", clazz.getSimpleName());
            return;
        }
        if (!CLASSES.containsKey(clazz)) {
            if (clazz.isAnnotationPresent(Tagged.class)) {
                CLASSES.put(clazz, clazzInstance);
                LOGGER.debug("addClass", "Trida %s byla pridana do seznamu", clazz.getSimpleName());
            } else {
                if (!clazz.equals(Config.class)) {
                    LOGGER.warning("addClass",
                            "Trida %s neni oznacena jako %s",
                            clazz.getSimpleName(),
                            Tagged.class.getSimpleName());
                }
            }
        } else {
            LOGGER.warning("addClass", "Seznam trid jiz tridu %s obsahuje", clazz.getSimpleName());
        }
    }

    /**
     * Nacte znovu vsechny tagy ze seznamu trid a nahradi v konfiguracnim souboru tagy za hodnoty.
     */
    public static void rebuild()
    {
        TAGS.clear();

        for (Class<?> clazz : CLASSES.keySet()) {
            addTagsFromClass(clazz, CLASSES.get(clazz));
        }
        for (Class<?> clazz : CLASSES.keySet()) {
            translateClass(clazz, CLASSES.get(clazz));
        }
        LOGGER.debug("rebuild", "Hotovo");
    }

    /**
     * Ziska podle tagu hodnotu instancni promenne, ktera je oznacena timto tagem. Pokud takovy tag nebude existovat,
     * tak vraci NULL.
     * 
     * @param tag
     * @return
     */
    public static Object get(String tag)
    {
        return (tag == null) ? null : TAGS.get(tag.toUpperCase());
    }

    /**
     * Nahradi vsechny tagy v textu za hodnoty.
     * 
     * @param inputText
     * @return
     */
    public static String replaceTags(String inputText)
    {
        if (inputText == null) {
            return inputText;
        }

        final Matcher matcher = PATTERN_TAG.matcher(inputText);
        while (matcher.find()) {
            String tag = matcher.group(2);
            Object value = get(tag);
            inputText = inputText.replace(matcher.group(1) + tag + matcher.group(3), value.toString());
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

    /**
     * Prida vsechny tagy instancnich promennych ze tridy do seznamu tagu.
     * 
     * @param clazz
     * @param clazzInstance
     */
    private static void addTagsFromClass(Class<?> clazz, Object clazzInstance)
    {
        final Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (!field.isAnnotationPresent(Tag.class)) {
                continue;
            }
            Tag tag = field.getAnnotation(Tag.class);
            String tagValue = tag.value().toUpperCase();
            String classTagValue = clazz.getAnnotation(Tagged.class).value().toUpperCase();
            String fullTag = classTagValue.isEmpty() ? tagValue : String.format("%s::%s", classTagValue, tagValue);

            // u instancnich promennych musi byt vyplnen tag
            if (tagValue.isEmpty()) {
                continue;
            }
            LOGGER.debug("addTagsFromClass", "Nacten tag %s pro %s", fullTag, field.getName());

            try {
                if (TAGS.containsKey(fullTag)) {
                    LOGGER.warning("addTagsFromClass", "Tag %s jiz existuje", fullTag);
                } else {
                    Object value = field.get(clazzInstance);

                    if (value == null) {
                        LOGGER.info("addTagsFromClass", "Tag %s ma hodnotu NULL", fullTag);
                    } else {
                        TAGS.put(fullTag, value);
                        LOGGER.debug("addTagsFromClass", "Tag %s byl pridan s hodnotou %s", fullTag, value);
                    }
                }
            } catch (IllegalAccessException e) {
                LOGGER.error("addTagsFromClass", "Hodnotu pro tag %s nelze pridat", fullTag);
                LOGGER.debug(e);
            }
        }
    }

    /**
     * Nahradi tagy v hodnotach instancnich promennych ve tride, ktere obsahuji nejaky tag.
     * 
     * @param clazz
     * @param classInstance
     */
    @SuppressWarnings({ "unchecked" })
    private static void translateClass(Class<?> clazz, Object classInstance)
    {
        final Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.isAnnotationPresent(Tag.class)) {
                Tag alias = field.getAnnotation(Tag.class);

                if (alias.value().isEmpty()) {
                    continue;
                }
                try {
                    Object value = field.get(classInstance);

                    if (value == null) {
                        continue;
                    }
                    if (value instanceof List) {
                        List<String> list = (List<String>) value;

                        for (int i = 0; i < list.size(); i++) {
                            String translate = replaceTags(list.get(i));
                            list.set(i, translate);
                        }
                    } else if (!(value instanceof String)) {
                        field.set(classInstance, value);
                    } else {
                        String translate = replaceTags(value.toString()); // hodnota by mela byt primitivni typ
                        field.set(classInstance, translate);
                    }
                } catch (Exception e) {
                    LOGGER.error("translateClass", "Nastala chyba pri prekladu tridy %s", clazz.getSimpleName());
                    LOGGER.debug(e);
                }
            }
        }
    }
}
