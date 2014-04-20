package cz.mimic.mclauncher;

import cz.mimic.mclauncher.base.Json;
import cz.mimic.mclauncher.base.Minecraft;
import cz.mimic.mclauncher.config.Config;

public final class McLauncher
{
    private static final String JSON_FILE = "minecraft.json";

    public static void main(String[] args)
    {
        // Json.save(JSON_FILE, new Config());
        Config config = Json.load(JSON_FILE);

        Minecraft mc = new Minecraft(config);
        Thread th = new Thread(mc);
        th.start();
    }
}
