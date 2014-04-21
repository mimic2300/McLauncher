package cz.mimic.mclauncher.config.example;

import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.config.ConfigApplication;
import cz.mimic.mclauncher.config.ConfigJava;

public enum ExampleConfig
{
    MC172("172") {
        @Override
        public Config config()
        {
            Config config = new Config();

            config.application = APPLICATION_CONFIG;
            config.java = JAVA_CONFIG;

            config.minecraft.gameDir = "%appdata%\\.minecraft";
            config.minecraft.assetsDir = "{game_dir}\\assets\\virtual\\legacy";
            config.minecraft.librariesDir = "{game_dir}\\libraries";
            config.minecraft.nativesDir = "{game_dir}\\versions\\{version}\\{version}-natives";
            config.minecraft.mainClass = "net.minecraft.client.main.Main";
            config.minecraft.username = "mimic";
            config.minecraft.version = "1.7.2";
            config.minecraft.accessToken = "1337535510N";
            config.minecraft.customLibs.add("{game_dir}\\versions\\{version}\\{version}.jar");

            return config;
        }
    },
    MC172_FORGE("172f") {
        @Override
        public Config config()
        {
            Config config = new Config();

            config.application = APPLICATION_CONFIG;
            config.java = JAVA_CONFIG;

            config.minecraft.gameDir = "%appdata%\\.minecraft";
            config.minecraft.assetsDir = "{game_dir}\\assets\\virtual\\legacy";
            config.minecraft.librariesDir = "{game_dir}\\libraries";
            config.minecraft.nativesDir = "{game_dir}\\versions\\{version}\\{version}-natives";
            config.minecraft.mainClass = "net.minecraft.launchwrapper.Launch";
            config.minecraft.username = "mimic";
            config.minecraft.version = "1.7.2-Forge10.12.1.1061";
            config.minecraft.accessToken = "1337535510N";
            config.minecraft.customLibs.add("{game_dir}\\versions\\{version}\\{version}.jar");
            config.minecraft.customParameters.put("tweakClass", "cpw.mods.fml.common.launcher.FMLTweaker");

            return config;
        }
    };

    private static final ConfigApplication APPLICATION_CONFIG;
    private static final ConfigJava JAVA_CONFIG;

    static {
        APPLICATION_CONFIG = new ConfigApplication();
        APPLICATION_CONFIG.runFormat = "cmd /k start \"%s\" /min %s";
        APPLICATION_CONFIG.closeFormat = "taskkill /FI \"WINDOWTITLE eq %s*\"";
        APPLICATION_CONFIG.delayBeforeDelete = "3000";
        APPLICATION_CONFIG.uniqueTitle = "17777d4fc9b804df4755d024b0f6860f";

        JAVA_CONFIG = new ConfigJava();
        JAVA_CONFIG.javawDir = "%programfiles%\\Java\\jre8\\bin\\javaw.exe";
        JAVA_CONFIG.xms = "512M";
        JAVA_CONFIG.xmx = "2G";
    }

    private String id;

    private ExampleConfig(String id)
    {
        this.id = id;
    }

    public String id()
    {
        return id;
    }

    public abstract Config config();

    public static ExampleConfig findById(String id)
    {
        for (ExampleConfig conf : values()) {
            if (conf.id.equals(id)) {
                return conf;
            }
        }
        return null;
    }
}
