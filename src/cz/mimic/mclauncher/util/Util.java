package cz.mimic.mclauncher.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cz.mimic.mclauncher.config.util.Parameter;
import cz.mimic.mclauncher.logger.Logger;

/**
 * Pomocne metody.
 * 
 * @author mimic
 */
public final class Util
{
    private static final Logger LOGGER = new Logger(Util.class);

    private Util()
    {}

    /**
     * Ziska vsechny parametry ze tridy jako string. Beze pouze instancni promenne, ktere jsou oznaceny jako parametr.
     * 
     * @param parameters
     * @return
     */
    public static String getParametersString(Object clazzInstance)
    {
        if (clazzInstance == null) {
            LOGGER.error("getParametersString", "Instance tridy je NULL");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Field[] fields = clazzInstance.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (!field.isAnnotationPresent(Parameter.class)) {
                continue;
            }
            try {
                Parameter param = field.getAnnotation(Parameter.class);
                Object value = field.get(clazzInstance);

                if (value == null) {
                    continue;
                }
                if (param.quotationMarks()) {
                    sb.append(String.format("--%s \"%s\"", param.value(), value)).append(" ");
                } else {
                    sb.append(String.format("--%s %s", param.value(), value)).append(" ");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                LOGGER.error("getParametersString",
                        "Nepodarilo se ziskat hodnotu z instancni promenne %s",
                        field.getName());
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
