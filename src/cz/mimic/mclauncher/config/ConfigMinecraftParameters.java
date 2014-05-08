package cz.mimic.mclauncher.config;

import cz.mimic.mclauncher.config.util.Parameter;
import cz.mimic.mclauncher.tag.Tag;
import cz.mimic.mclauncher.tag.Tagged;

/**
 * Dostupne parametry pri spusteni minecraftu.
 * 
 * @author mimic
 */
@Tagged("mc::param")
public class ConfigMinecraftParameters
{
    @Parameter("username")
    @Tag("username")
    public String username;

    @Parameter("session")
    @Tag("session")
    public String session; // Od verze 1.7.0 prejmenovano na accessToken.

    @Parameter("version")
    @Tag("version")
    public String version;

    @Parameter("accessToken")
    @Tag("accessToken")
    public String accessToken;

    @Parameter("tweakClass")
    @Tag("tweakClass")
    public String tweakClass;

    /**
     * Vytvori instanci nastaveni parametru pro minecraft.
     */
    public ConfigMinecraftParameters()
    {}

    /**
     * Vytvori kopii nastaveni parametru pro minecraft.
     * 
     * @param c
     */
    public ConfigMinecraftParameters(ConfigMinecraftParameters c)
    {
        username = c.username;
        version = c.version;
        session = c.session;
        accessToken = c.accessToken;
        tweakClass = c.tweakClass;
    }
}
