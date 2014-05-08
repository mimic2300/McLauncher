package cz.mimic.mclauncher.logger;

/**
 * Slouzi pro logovani ruznych udalosti.
 * 
 * @author mimic
 */
public class Logger
{
    private static int level = LoggingLevel.getFlag(LoggingLevel.ERROR); // ve vychozim stavu vypisuje jen chyby

    private final Class<?> clazz;

    /**
     * Vytvori instanci logeru.
     * 
     * @param clazz
     */
    public Logger(final Class<?> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * Zaloguje chybovou zpravu.
     * 
     * @param message
     * @param params
     */
    public void error(String message, Object... params)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.ERROR)) {
            System.out.format("[E] %s%n", String.format(message, params));
        }
    }

    /**
     * Zaloguje chybovou zpravu.
     * 
     * @param method
     * @param message
     * @param params
     */
    public void error(String method, String message, Object... params)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.ERROR)) {
            System.out.format("[E] %s.%s :: %s%n", clazz.getSimpleName(), method, String.format(message, params));
        }
    }

    /**
     * Zaloguje varovnou zpravu.
     * 
     * @param message
     * @param params
     */
    public void warning(String message, Object... params)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.WARNING)) {
            System.out.format("[W] %s%n", String.format(message, params));
        }
    }

    /**
     * Zaloguje varovnou zpravu.
     * 
     * @param method
     * @param message
     * @param params
     */
    public void warning(String method, String message, Object... params)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.WARNING)) {
            System.out.format("[W] %s.%s :: %s%n", clazz.getSimpleName(), method, String.format(message, params));
        }
    }

    /**
     * Zaloguje informacni zpravu.
     * 
     * @param message
     * @param params
     */
    public void info(String message, Object... params)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.INFO)) {
            System.out.format("[I] %s%n", String.format(message, params));
        }
    }

    /**
     * Zaloguje informacni zpravu.
     * 
     * @param method
     * @param message
     * @param params
     */
    public void info(String method, String message, Object... params)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.INFO)) {
            System.out.format("[I] %s.%s :: %s%n", clazz.getSimpleName(), method, String.format(message, params));
        }
    }

    /**
     * Zaloguje debugovou zpravu.
     * 
     * @param message
     * @param params
     */
    public void debug(String message, Object... params)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.DEBUG)) {
            System.out.format("[D] %s%n", String.format(message, params));
        }
    }

    /**
     * Zaloguje debugovou zpravu.
     * 
     * @param method
     * @param message
     * @param params
     */
    public void debug(String method, String message, Object... params)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.DEBUG)) {
            System.out.format("[D] %s.%s :: %s%n", clazz.getSimpleName(), method, String.format(message, params));
        }
    }

    /**
     * Zaloguje debugovou zpravu.
     * 
     * @param ex
     */
    public void debug(Exception ex)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.DEBUG)) {
            if (ex != null) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Nastavi uroven logovani.
     * 
     * @param levels
     */
    public static void setLoggingLevel(LoggingLevel... loggingLevels)
    {
        level = LoggingLevel.getFlag(loggingLevels);
    }

    /**
     * Nastavi uroven logovani.
     * 
     * @param loggingLevel
     */
    public static void setLoggingLevel(String loggingLevel)
    {
        level = LoggingLevel.getByName(loggingLevel).flag();
    }
}
