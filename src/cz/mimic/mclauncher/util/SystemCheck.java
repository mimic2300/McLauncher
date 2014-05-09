package cz.mimic.mclauncher.util;

/**
 * Kontrola typu operacniho systemu.
 * 
 * @author mimic
 */
public final class SystemCheck
{
    private static final String OS = System.getProperty("os.name").toLowerCase();

    private SystemCheck()
    {}

    /**
     * Je operacni system pro aplikaci povolen?
     * 
     * @return
     */
    public static boolean isSupported()
    {
        return getCurrent().isSupported();
    }

    /**
     * Je operacni system Windows?
     * 
     * @return
     */
    public static boolean isWindows()
    {
        return (OS.indexOf("win") >= 0);
    }

    /**
     * Je operacni system Mac OS X?
     * 
     * @return
     */
    public static boolean isMac()
    {
        return (OS.indexOf("mac") >= 0);
    }

    /**
     * Je operacni system nejake distribuce Linuxu/Unixu?
     * 
     * @return
     */
    public static boolean isUnix()
    {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    /**
     * Je operacni system Solaris?
     * 
     * @return
     */
    public static boolean isSolaris()
    {
        return (OS.indexOf("sunos") >= 0);
    }

    private static SystemType getCurrent()
    {
        if (isWindows()) {
            return SystemType.WINDOWS;
        } else if (isMac()) {
            return SystemType.MAC;
        } else if (isUnix()) {
            return SystemType.UNIX;
        } else if (isSolaris()) {
            return SystemType.SOLARIS;
        } else {
            return SystemType.UNKNOWN;
        }
    }
}
