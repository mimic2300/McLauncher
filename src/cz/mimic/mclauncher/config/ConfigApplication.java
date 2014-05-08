package cz.mimic.mclauncher.config;

import cz.mimic.mclauncher.tag.Tag;
import cz.mimic.mclauncher.tag.Tagged;

@Tagged("app")
public final class ConfigApplication
{
    @Tag("runFormat")
    public String runFormat;

    @Tag("closeFormat")
    public String closeFormat;

    @Tag("delayBeforeDelete")
    public String delayBeforeDelete;

    @Tag("uniqueTitle")
    public String uniqueTitle;

    @Tag("removeLogs")
    public String removeLogs;

    @Tag("loggingLevel")
    public String loggingLevel;

    /**
     * Vytvori instanci konfigurace aplikace.
     */
    public ConfigApplication()
    {}

    /**
     * Vytvori kopii konfigurace aplikace.
     * 
     * @param c
     */
    public ConfigApplication(ConfigApplication c)
    {
        runFormat = c.runFormat;
        closeFormat = c.closeFormat;
        delayBeforeDelete = c.delayBeforeDelete;
        uniqueTitle = c.uniqueTitle;
        removeLogs = c.removeLogs;
        loggingLevel = c.loggingLevel;
    }

    /**
     * Ma byt povoleno automaticke odebrani logu?
     * 
     * @return
     */
    public boolean isRemoveLogs()
    {
        if (removeLogs == null) {
            removeLogs = "true"; // vychozi hodnota
        }
        return removeLogs.equalsIgnoreCase("true");
    }
}
