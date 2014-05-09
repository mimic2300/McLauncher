package cz.mimic.mclauncher.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Slouzi pro logovani ruznych udalosti.
 * 
 * @author mimic
 */
public class Logger
{
    private static final String LOG_FILE = "mcLauncher.log";

    private static int level = LoggingLevel.getFlag(LoggingLevel.ERROR); // ve vychozim stavu vypisuje jen chyby
    private static FileWriter fileWriter;
    private static BufferedWriter bufferedWriter;

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
     * Vytvori a otevre soubor pro zapis.
     */
    public static void open()
    {
        Path path = Paths.get(LOG_FILE);

        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
            Files.createFile(path);

            fileWriter = new FileWriter(new File(LOG_FILE));
            bufferedWriter = new BufferedWriter(fileWriter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uzavre stream pro zapis do logu.
     */
    public static void close()
    {
        try {
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            write(String.format("[ERROR] %s%n", String.format(message, params)));
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
            write(String.format("[ERROR] %s.%s :: %s%n", clazz.getSimpleName(), method, String.format(message, params)));
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
            write(String.format("[WARNING] %s%n", String.format(message, params)));
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
            write(String.format("[WARNING] %s.%s :: %s%n",
                    clazz.getSimpleName(),
                    method,
                    String.format(message, params)));
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
            write(String.format("[INFO] %s%n", String.format(message, params)));
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
            write(String.format("[INFO] %s.%s :: %s%n", clazz.getSimpleName(), method, String.format(message, params)));
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
            write(String.format("[DEBUG] %s%n", String.format(message, params)));
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
            write(String.format("[DEBUG] %s.%s :: %s%n", clazz.getSimpleName(), method, String.format(message, params)));
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
     * Zaloguje minecraft zpravu.
     * 
     * @param method
     * @param message
     * @param params
     */
    public void minecraft(String message)
    {
        if (LoggingLevel.hasLevel(level, LoggingLevel.MINECRAFT)) {
            write(String.format("[MC] %s%n", message));
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
     * @param loggingFlag
     */
    public static void setLoggingLevel(int loggingFlag)
    {
        level = loggingFlag;
    }

    /**
     * Nastavi uroven logovani.
     * 
     * @param loggingLevel
     */
    public static void setLoggingLevel(String loggingLevel)
    {
        List<LoggingLevel> levels = LoggingLevel.getLevels(loggingLevel);
        level = LoggingLevel.getFlag(levels);
    }

    private static void write(String message)
    {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.write(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.print(message);
    }
}
