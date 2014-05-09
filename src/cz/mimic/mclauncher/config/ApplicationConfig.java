package cz.mimic.mclauncher.config;

import cz.mimic.mclauncher.McLauncher;
import cz.mimic.mclauncher.logger.LoggingLevel;
import cz.mimic.mclauncher.tag.Tag;
import cz.mimic.mclauncher.tag.Tagged;

@Tagged("app")
public final class ApplicationConfig
{
    @Tag("version")
    public String version;

    @Tag("removeLogs")
    public Boolean removeLogs;

    @Tag("consoleOutput")
    public Boolean consoleOutput;

    @Tag("loggingLevel")
    public String loggingLevel; // uchovava seznam urovni oddeleny carkou

    /**
     * Vytvori instanci konfigurace aplikace.
     */
    public ApplicationConfig()
    {
        version = McLauncher.VERSION;
        removeLogs = true;
        consoleOutput = false;
        loggingLevel = LoggingLevel.DEFAULT.name();
    }

    /**
     * Vytvori kopii konfigurace aplikace.
     * 
     * @param c
     */
    public ApplicationConfig(ApplicationConfig c)
    {
        version = c.version;
        removeLogs = c.removeLogs;
        consoleOutput = c.consoleOutput;
        loggingLevel = c.loggingLevel;
    }
}
