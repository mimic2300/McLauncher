package cz.mimic.mclauncher.minecraft;

import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.config.ConfigApplication;
import cz.mimic.mclauncher.config.ConfigJava;
import cz.mimic.mclauncher.config.ConfigMinecraft;
import cz.mimic.mclauncher.config.ConfigMinecraftDirectories;
import cz.mimic.mclauncher.config.ConfigMinecraftParameters;
import cz.mimic.mclauncher.logger.Logger;
import cz.mimic.mclauncher.logger.LoggingLevel;
import cz.mimic.mclauncher.util.Message;

/**
 * Vytvari konfiguracni soubory pro aplikaci.
 * 
 * @author mimic
 */
public final class MinecraftVersionFactory
{
    private static final Logger LOGGER = new Logger(Minecraft.class);

    private MinecraftVersionFactory()
    {}

    /**
     * Ziska konfiguracni soubor podle verze minecraftu.
     * 
     * @param version
     * @return
     */
    public static Config createConfig(MinecraftVersion version)
    {
        Config config = new Config();

        setDefaultApplicationConfig(config);
        setDefaultJavaConfig(config);
        setDefaultMinecraftConfig(config);

        switch (version) {
            case MC_1_6_4_FORGE_11_1_965:
                MinecraftVersion.MC_1_6_4_FORGE_11_1_965.applyVersionChanges(config.minecraft);
                break;

            default:
                String message = String.format("Verze minecraftu %s neni zatim naimplementovana", version);
                LOGGER.error("createConfig", message);
                Message.error(message);
                return null;
        }
        return config;
    }

    private static void setDefaultApplicationConfig(Config config)
    {
        ConfigApplication application = new ConfigApplication();
        application.runFormat = "cmd /k start \"%s\" /min %s"; // 1 %s je uniqueTitle, 2 %s je absolutni cesta k .bat
        application.closeFormat = "taskkill /FI \"WINDOWTITLE eq %s*\""; // %s je uniqueTitle
        application.delayBeforeDelete = "3000"; // ms
        application.uniqueTitle = "17777d4fc9b804df4755d024b0f6860f";
        application.removeLogs = "true";
        application.loggingLevel = LoggingLevel.ERROR.name();

        config.application = application;
    }

    private static void setDefaultJavaConfig(Config config)
    {
        ConfigJava java = new ConfigJava();
        java.javawDir = "%programfiles%\\Java\\jre8\\bin\\javaw.exe";
        java.xms = "512M";
        java.xmx = "2G";

        config.java = java;
    }

    private static void setDefaultMinecraftConfig(Config config)
    {
        ConfigMinecraft minecraft = new ConfigMinecraft();
        minecraft.mainClass = "net.minecraft.client.main.Main";

        minecraft.directories = new ConfigMinecraftDirectories();
        minecraft.directories.game = "%appdata%\\.minecraft";
        minecraft.directories.assets = "{mc::dir::game}\\assets";
        minecraft.directories.libraries = "{mc::dir::game}\\libraries";
        minecraft.directories.natives = "{mc::dir::game}\\versions\\{mc::param::version}\\{mc::param::version}-natives";

        minecraft.parameters = new ConfigMinecraftParameters();
        minecraft.parameters.username = "player";

        config.minecraft = minecraft;
    }
}
