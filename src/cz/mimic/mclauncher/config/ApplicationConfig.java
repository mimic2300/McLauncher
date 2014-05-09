package cz.mimic.mclauncher.config;

import cz.mimic.mclauncher.tag.Tag;
import cz.mimic.mclauncher.tag.Tagged;

@Tagged("app")
public final class ApplicationConfig
{
    @Tag("removeLogs")
    public Boolean removeLogs;

    @Tag("showConsole")
    public Boolean showConsole;

    @Tag("loggingLevel")
    public String loggingLevel; // uchovava seznam urovni oddeleny carkou

    /**
     * Vytvori instanci konfigurace aplikace.
     */
    public ApplicationConfig()
    {}

    /**
     * Vytvori kopii konfigurace aplikace.
     * 
     * @param c
     */
    public ApplicationConfig(ApplicationConfig c)
    {
        removeLogs = c.removeLogs;
        showConsole = c.showConsole;
        loggingLevel = c.loggingLevel;
    }
}
