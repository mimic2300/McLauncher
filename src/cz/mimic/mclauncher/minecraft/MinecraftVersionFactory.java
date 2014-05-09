package cz.mimic.mclauncher.minecraft;

import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.config.ApplicationConfig;
import cz.mimic.mclauncher.config.JavaConfig;
import cz.mimic.mclauncher.config.MinecraftConfig;
import cz.mimic.mclauncher.config.MinecraftDirectoriesConfig;
import cz.mimic.mclauncher.config.MinecraftParametersConfig;
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
        ApplicationConfig application = new ApplicationConfig();
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
        JavaConfig java = new JavaConfig();
        java.path = "%programfiles%\\Java\\jre8\\bin\\java.exe";
        java.xms = "512M";
        java.xmx = "2G";

        config.java = java;
    }

    private static void setDefaultMinecraftConfig(Config config)
    {
        MinecraftConfig minecraft = new MinecraftConfig();
        minecraft.mainClass = "net.minecraft.client.main.Main";

        minecraft.directories = new MinecraftDirectoriesConfig();
        minecraft.directories.game = "%appdata%\\.minecraft";
        minecraft.directories.assets = "{mc::dir::game}\\assets";
        minecraft.directories.libraries = "{mc::dir::game}\\libraries";
        minecraft.directories.natives = "{mc::dir::game}\\versions\\{mc::param::version}\\{mc::param::version}-natives";

        minecraft.parameters = new MinecraftParametersConfig();
        minecraft.parameters.username = "player";

        config.minecraft = minecraft;
    }
}
