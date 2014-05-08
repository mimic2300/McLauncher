package cz.mimic.mclauncher.logger;

import java.util.ArrayList;
import java.util.List;

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
     * Loguje vse.
     */
    ALL(0xF);

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
    public static int getFlag(LoggingLevel... levels)
    {
        int value = 0;

        for (LoggingLevel level : levels) {
            value |= level.flag;
        }
        return value;
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
     * Zkontroluje, jestli flag obsahuje nejakou uroven logovani.
     * 
     * @param flags
     * @param level
     * @return
     */
    public static boolean hasLevel(int flags, LoggingLevel... levels)
    {
        for (LoggingLevel level : levels) {
            if ((flags & level.flag) != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ziska uroven logovani podle nazvu typu. Pokud takovy typ nebude existovat, tak vrati vychozi uroven ERROR.
     * 
     * @param name
     * @return
     */
    public static LoggingLevel getByName(String name)
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
