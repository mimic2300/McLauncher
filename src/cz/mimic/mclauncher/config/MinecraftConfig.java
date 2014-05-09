package cz.mimic.mclauncher.config;

import java.util.ArrayList;
import java.util.List;

import cz.mimic.mclauncher.tag.Tag;
import cz.mimic.mclauncher.tag.Tagged;

/**
 * Konfigurace pro minecraft.
 * 
 * @author mimic
 */
@Tagged("mc")
public final class MinecraftConfig
{
    @Tag("mainClass")
    public String mainClass;

    public MinecraftDirectoriesConfig directories;
    public MinecraftParametersConfig parameters;

    @Tag("customLibs")
    public List<String> customLibs;

    @Tag("availableLibs")
    public List<String> availableLibs;

    /**
     * Vytvori instanci konfigurace pro minecraft.
     */
    public MinecraftConfig()
    {
        mainClass = "net.minecraft.client.main.Main";
        directories = new MinecraftDirectoriesConfig();
        parameters = new MinecraftParametersConfig();
        customLibs = null;
        availableLibs = null; // nacte se pozdeji automaticky
    }

    /**
     * vytvori kopii konfigurace pro minecraft.
     * 
     * @param c
     */
    public MinecraftConfig(MinecraftConfig c)
    {
        mainClass = c.mainClass;
        directories = new MinecraftDirectoriesConfig(c.directories);
        parameters = new MinecraftParametersConfig(c.parameters);

        if (c.customLibs != null) {
            customLibs = new ArrayList<String>(c.customLibs);
        }
        if (c.availableLibs != null) {
            availableLibs = new ArrayList<String>(c.availableLibs);
        }
    }

    /**
     * Vytvori ze seznamu knihoven jeden string, kde kazda knihovna bude oddelena strednikem.
     * 
     * @return
     */
    public String getAvailableLibsString()
    {
        return prepareLibsString(availableLibs);
    }

    /**
     * Vytvori ze seznamu vlastnich knihoven jeden string, kde kazda knihovna bude oddelena strednikem.
     * 
     * @return
     */
    public String getCustomLibsString()
    {
        return prepareLibsString(customLibs);
    }

    /**
     * Vytvori ze seznamu knihoven a vlastnich knihoven jeden string, kde kazda knihovna bude oddelena strednikem.
     * 
     * @return
     */
    public String getAllLibsString()
    {
        return getAvailableLibsString() + getCustomLibsString();
    }

    private String prepareLibsString(List<String> libs)
    {
        if (libs == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for (String lib : libs) {
            sb.append(lib).append(";");
        }
        return sb.toString();
    }
}
