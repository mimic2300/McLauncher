package cz.mimic.mclauncher.logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Uroven logovani.
 * 
 * @author mimic
 */
public enum LoggingLevel
{
    /**
     * Loguje pouze informacni hlasky.
     */
    INFO(0x1),

    /**
     * Loguje pouze varovne hlasky.
     */
    WARNING(0x2),

    /**
     * Loguje pouze chybove hlasky.
     */
    ERROR(0x4),

    /**
     * Loguje pouze debugove hlasky.
     */
    DEBUG(0x8),

    /**
     * Loguje samotny vystup minecraftu.
     */
    MINECRAFT(0x10),

    /**
     * Loguje vse.
     */
    ALL(0x1 | 0x2 | 0x4 | 0x8 | 0x10),

    /**
     * Vychozi uroven logovani.
     */
    DEFAULT(0x2 | 0x4);

    private static final Logger LOGGER = new Logger(LoggingLevel.class);

    private int flag;

    private LoggingLevel(int flag)
    {
        this.flag = flag;
    }

    /**
     * Ziska flag urovne logovani.
     * 
     * @return
     */
    public int flag()
    {
        return flag;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return name();
    }

    /**
     * Ziska hodnotu flagu pro seznam urovni logovani.
     * 
     * @param levels
     * @return
     */
    public static int getFlag(List<LoggingLevel> levels)
    {
        int value = 0;

        for (LoggingLevel level : levels) {
            value |= level.flag;
        }
        return value;
    }

    /**
     * Ziska hodnotu flagu pro seznam urovni logovani.
     * 
     * @param levels
     * @return
     */
    public static int getFlag(LoggingLevel... levels)
    {
        return getFlag(Arrays.asList(levels));
    }

    /**
     * Ziska seznam urovni logovani z flagu.
     * 
     * @param flag
     * @return
     */
    public static List<LoggingLevel> getLevels(int flag)
    {
        List<LoggingLevel> levels = new ArrayList<LoggingLevel>();

        for (LoggingLevel level : values()) {
            if ((level.flag & flag) == level.flag) {
                levels.add(level);
            }
        }
        return levels;
    }

    /**
     * Ziska seznam urovni logovani z stringu, kde jsou jednotlive urovne oddeleny carkou.
     * 
     * @param loggingLevel
     * @return
     */
    public static List<LoggingLevel> getLevels(String loggingLevel)
    {
        Set<LoggingLevel> levels = new HashSet<LoggingLevel>();
        String[] tokens = loggingLevel.split(",");

        for (String token : tokens) {
            LoggingLevel level = getByName(token.replaceAll("\\s+", ""));
            levels.add(level);
        }
        return new ArrayList<LoggingLevel>(levels);
    }

    /**
     * Zkontroluje, jestli flag obsahuje nejakou uroven logovani.
     * 
     * @param flags
     * @param levels
     * @return
     */
    public static boolean hasLevel(int flags, List<LoggingLevel> levels)
    {
        int levelsFlag = getFlag(levels);

        if ((flags & levelsFlag) == levelsFlag) {
            return true;
        }
        return false;
    }

    /**
     * Zkontroluje, jestli flag obsahuje nejakou uroven logovani.
     * 
     * @param flags
     * @param levels
     * @return
     */
    public static boolean hasLevel(int flags, LoggingLevel... levels)
    {
        return hasLevel(flags, Arrays.asList(levels));
    }

    private static LoggingLevel getByName(String name)
    {
        for (LoggingLevel level : values()) {
            if (level.name().equalsIgnoreCase(name)) {
                return level;
            }
        }
        LOGGER.warning("getByName",
                "%s je neplatny nazev urovne logovani a bude tak nastavena vychozi hodnota - ERROR",
                name);
        return ERROR;
    }
}
