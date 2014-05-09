package cz.mimic.mclauncher.config;

import cz.mimic.mclauncher.config.util.Parameter;
import cz.mimic.mclauncher.tag.Tag;
import cz.mimic.mclauncher.tag.Tagged;

/**
 * Ruzne cesty adresaru pro minecraft.
 * 
 * @author mimic
 */
@Tagged("mc::dir")
public class MinecraftDirectoriesConfig
{
    @Parameter(value = "game", quotationMarks = true)
    @Tag("game")
    public String game;

    @Tag("libraries")
    public String libraries;

    @Parameter(value = "assets", quotationMarks = true)
    @Tag("assets")
    public String assets;

    @Tag("natives")
    public String natives;

    /**
     * Vytvori instanci nastaveni adresaru pro minecraft.
     */
    public MinecraftDirectoriesConfig()
    {
        game = "%appdata%\\.minecraft";
        assets = "{mc::dir::game}\\assets";
        libraries = "{mc::dir::game}\\libraries";
        natives = "{mc::dir::game}\\versions\\{mc::param::version}\\{mc::param::version}-natives";
    }

    /**
     * Vytvori kopii nastaveni adresaru pro minecraft.
     * 
     * @param c
     */
    public MinecraftDirectoriesConfig(MinecraftDirectoriesConfig c)
    {
        game = c.game;
        libraries = c.libraries;
        assets = c.assets;
        natives = c.natives;
    }
}
