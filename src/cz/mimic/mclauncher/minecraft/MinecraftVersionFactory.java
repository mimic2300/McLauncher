package cz.mimic.mclauncher.minecraft;

import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.logger.Logger;
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

        switch (version) {
            case MC_1_6_4_FORGE_11_1_965:
                MinecraftVersion.MC_1_6_4_FORGE_11_1_965.overrideConfig(config.minecraft);
                break;

            default:
                String message = String.format("Verze minecraftu %s zatim neni implementovana", version);
                LOGGER.error("createConfig", message);
                Message.error(message);
                return null;
        }
        return config;
    }
}
