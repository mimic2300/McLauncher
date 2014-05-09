package cz.mimic.mclauncher.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Typy operacnich systemu.
 * 
 * @author mimic
 */
public enum SystemType
{
    WINDOWS("Windows", true),
    MAC("Mac OSX", false),
    UNIX("Unix/Linux", false),
    SOLARIS("Solaris", false),
    UNKNOWN("Unknown", false);

    private String alias;
    private boolean supported;

    private SystemType(String alias, boolean supported)
    {
        this.alias = alias;
        this.supported = supported;
    }

    /**
     * Je operacni system aplikaci podporovan?
     * 
     * @return
     */
    public boolean supported()
    {
        return supported;
    }

    /**
     * Ziska citelny nazev operacniho systemu.
     * 
     * @return
     */
    public String alias()
    {
        return alias;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return alias();
    }

    /**
     * Ziska seznam podporovanych operacnich systemu.
     * 
     * @return
     */
    public static List<SystemType> supportedSystems()
    {
        List<SystemType> systems = new ArrayList<SystemType>();

        for (SystemType type : values()) {
            if (type == UNKNOWN) {
                continue;
            }
            if (type.supported) {
                systems.add(type);
            }
        }
        return systems;
    }
}
