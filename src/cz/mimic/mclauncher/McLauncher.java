package cz.mimic.mclauncher;

import cz.mimic.mclauncher.alias.AliasBuilder;
import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.config.ConfigApplication;
import cz.mimic.mclauncher.config.ConfigJava;
import cz.mimic.mclauncher.config.ConfigMinecraft;
import cz.mimic.mclauncher.config.example.ExampleConfig;
import cz.mimic.mclauncher.json.Json;

public final class McLauncher
{
    private static final String JSON_FILE = "minecraft.json";

    public static void main(String[] args)
    {
        // allow build default config
        if (args.length == 1) {
            ExampleConfig example = ExampleConfig.findById(args[0].toLowerCase());

            if (example != null) {
                Json.save(JSON_FILE, example.config());
            }
        }
        Config config = Json.load(JSON_FILE);

        if (config == null) {
            return;
        }
        Config modifiedConfig = Json.load(JSON_FILE);

        AliasBuilder.addClass(Config.class, config);
        AliasBuilder.addClass(ConfigJava.class, config.java);
        AliasBuilder.addClass(ConfigMinecraft.class, config.minecraft);
        AliasBuilder.addClass(ConfigApplication.class, config.application);
        AliasBuilder.rebuild();

        // reload libs from libraries directory
        config.minecraft.libs = Minecraft.loadLibs(config.minecraft.librariesDir);
        modifiedConfig.minecraft.libs = config.minecraft.libs;
        Json.save(JSON_FILE, modifiedConfig);

        // start minecraft
        Minecraft mc = new Minecraft(config);
        new Thread(mc).start();
    }
}
