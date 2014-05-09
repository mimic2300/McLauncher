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

    @Tag("libs")
    public List<String> autoLoadedLibs;

    /**
     * Vytvori instanci konfigurace pro minecraft.
     */
    public MinecraftConfig()
    {}

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
        if (c.autoLoadedLibs != null) {
            autoLoadedLibs = new ArrayList<String>(c.autoLoadedLibs);
        }
    }

    /**
     * Vytvori ze seznamu knihoven jeden string, kde kazda knihovna bude oddelena strednikem.
     * 
     * @return
     */
    public String getAutoLoadedLibsString()
    {
        return prepareLibs(autoLoadedLibs);
    }

    /**
     * Vytvori ze seznamu vlastnich knihoven jeden string, kde kazda knihovna bude oddelena strednikem.
     * 
     * @return
     */
    public String getCustomLibsString()
    {
        return prepareLibs(customLibs);
    }

    /**
     * Vytvori ze seznamu knihoven a vlastnich knihoven jeden string, kde kazda knihovna bude oddelena strednikem.
     * 
     * @return
     */
    public String getAllLibsString()
    {
        return getAutoLoadedLibsString() + getCustomLibsString();
    }

    private String prepareLibs(List<String> libs)
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
