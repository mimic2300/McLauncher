package cz.mimic.mclauncher;

import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.config.ConfigApplication;
import cz.mimic.mclauncher.config.ConfigJava;
import cz.mimic.mclauncher.config.ConfigMinecraft;
import cz.mimic.mclauncher.config.ConfigMinecraftDirectories;
import cz.mimic.mclauncher.config.ConfigMinecraftParameters;
import cz.mimic.mclauncher.logger.Logger;
import cz.mimic.mclauncher.minecraft.Minecraft;
import cz.mimic.mclauncher.minecraft.MinecraftRunner;
import cz.mimic.mclauncher.minecraft.MinecraftVersion;
import cz.mimic.mclauncher.minecraft.MinecraftVersionFactory;
import cz.mimic.mclauncher.tag.TagBuilder;
import cz.mimic.mclauncher.ui.VersionSelector;
import cz.mimic.mclauncher.ui.component.util.Executor;
import cz.mimic.mclauncher.util.LogsRemover;

/*
 * - Pokud hra nepujde spustit, tak je potreba zkontrolovat adresar s knihovnami, jestli u nektere neexistuji 2
 * ruzne verze (napr. launchwrapper, lwjgl, ...), hra se potom nedokaze rozhodnou, kterou pouzit.
 * - TODO: Reseni je, ze se zkontroluji verze knihoven, aby pro kazdou existovala jen jedna, ale neni zaruceno,
 * ze hra bude fungovat spravne na te novejsi verzi knihovny (starsi by se smazaly nebo zakomentovaly)
 */

/**
 * Aplikace McLauncher.
 * 
 * @author mimic
 */
public final class McLauncher
{
    private static final Logger LOGGER = new Logger(TagBuilder.class);

    private static Config config;

    /**
     * Main ;-)
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        config = Config.load(Minecraft.DEFAULT_CONFIG_FILE);

        // pokud konfiguracni soubor neexistuje nebo bude mit spatny format, tak zobrazime okno s vyberem verzi
        // minecraftu a vygenerujem konfiguracni soubor
        while (config == null) {
            VersionSelector selector = new VersionSelector(new Executor()
            {
                @Override
                public void execute(Object source, Object userData)
                {
                    if (userData == null) {
                        // zde musi byt "error", protoze uroven logovani jsme neziskali a aby se neco vypsalo, tak error
                        // je vychozi uroven
                        LOGGER.error("main", "Verze minecraftu nebyla vybrana a aplikace bude ukoncena");
                        System.exit(1);
                    }
                    MinecraftVersion version = (MinecraftVersion) userData;
                    config = MinecraftVersionFactory.createConfig(version);

                    // muze nastat v pripade, ze verze existuje, ale neni nastavena ve factory
                    if (config == null) {
                        return;
                    }
                    Logger.setLoggingLevel(config.application.loggingLevel);

                    if (!Config.save(Minecraft.DEFAULT_CONFIG_FILE, config)) {
                        System.exit(1);
                    }
                }
            });
            selector.setVisible(true);
        }

        // vytvori kopii puvodni nactene konfigurace, protoze do te originalni upravime tagove hodnoty
        // do teto kopie se pozdeji prenactou knihovny a opet se ulozi
        Config returnConfig = new Config(config);

        // nacte vsechny tagy ze trid a upravi hodnoty konfiguracniho souboru "config"
        TagBuilder.addClass(Config.class, config);
        TagBuilder.addClass(ConfigJava.class, config.java);
        TagBuilder.addClass(ConfigMinecraft.class, config.minecraft);
        TagBuilder.addClass(ConfigMinecraftDirectories.class, config.minecraft.directories);
        TagBuilder.addClass(ConfigMinecraftParameters.class, config.minecraft.parameters);
        TagBuilder.addClass(ConfigApplication.class, config.application);
        TagBuilder.rebuild();

        // nacte knihovny z adresare podle nacteneho konfiguracniho souboru a soubor nize opet ulozi
        config.minecraft.autoLoadedLibs = Minecraft.loadLibraries(config.minecraft);
        returnConfig.minecraft.autoLoadedLibs = config.minecraft.autoLoadedLibs;

        if (!Config.save(Minecraft.DEFAULT_CONFIG_FILE, returnConfig)) {
            System.exit(1);
        }

        // odebere vsechny soubory s logy v adresari s hrou
        if (config.application.isRemoveLogs()) {
            LogsRemover.start(config.minecraft.directories.game);
        }

        // spusti minecraft
        MinecraftRunner mcRunner = new MinecraftRunner(config);
        new Thread(mcRunner).start();
    }
}
