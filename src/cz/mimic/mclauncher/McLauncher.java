package cz.mimic.mclauncher;

import cz.mimic.mclauncher.config.ApplicationConfig;
import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.config.JavaConfig;
import cz.mimic.mclauncher.config.MinecraftConfig;
import cz.mimic.mclauncher.config.MinecraftDirectoriesConfig;
import cz.mimic.mclauncher.config.MinecraftParametersConfig;
import cz.mimic.mclauncher.logger.Logger;
import cz.mimic.mclauncher.logger.LoggingLevel;
import cz.mimic.mclauncher.minecraft.Minecraft;
import cz.mimic.mclauncher.minecraft.MinecraftRunner;
import cz.mimic.mclauncher.minecraft.MinecraftVersion;
import cz.mimic.mclauncher.minecraft.MinecraftVersionFactory;
import cz.mimic.mclauncher.tag.TagBuilder;
import cz.mimic.mclauncher.ui.VersionSelector;
import cz.mimic.mclauncher.ui.component.util.Executor;
import cz.mimic.mclauncher.util.LogsRemover;
import cz.mimic.mclauncher.util.Message;
import cz.mimic.mclauncher.util.SystemCheck;
import cz.mimic.mclauncher.util.SystemType;

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
    private static final Logger LOGGER = new Logger(McLauncher.class);

    private static Config config;

    /**
     * Main ;-)
     * 
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        Logger.open();
        Logger.setLoggingLevel(LoggingLevel.DEFAULT);

        // kontrola typu operacniho systemu
        if (!SystemCheck.isSupported()) {
            String message = String.format("Vas operacni system neni aplikaci podporovan. Podporovane systemy jsou pouze %s",
                    SystemType.supportedSystems());
            LOGGER.error("main", message);
            Message.error(message);
            exit();
        }

        config = Config.load(Minecraft.CONFIG_FILE);

        // pokud konfiguracni soubor neexistuje nebo bude mit spatny format, tak zobrazime okno s vyberem verzi
        // minecraftu a vygenerujem konfiguracni soubor
        if (config == null) {
            VersionSelector selector = new VersionSelector(new Executor()
            {
                @Override
                public void execute(Object source, Object userData)
                {
                    if (userData == null) {
                        LOGGER.warning("main", "Verze minecraftu nebyla vybrana a aplikace bude ukoncena");
                        exit();
                    }
                    MinecraftVersion version = (MinecraftVersion) userData;
                    config = MinecraftVersionFactory.createConfig(version);

                    // muze nastat v pripade, ze verze existuje, ale neni nastavena ve factory
                    if (config == null) {
                        LOGGER.error("main", "Verze minecraftu neni zatim inicializovana");
                        return;
                    }
                    if (!Config.save(Minecraft.CONFIG_FILE, config)) {
                        exit();
                    }
                }
            });
            selector.setVisible(true);
        }
        Logger.setLoggingLevel(config.application.loggingLevel);

        // vytvori kopii puvodni nactene konfigurace, protoze do te originalni upravime tagove hodnoty
        // do teto kopie se pozdeji prenactou knihovny a opet se ulozi
        Config returnConfig = new Config(config);

        // nacte vsechny tagy ze trid a upravi hodnoty konfiguracniho souboru "config"
        TagBuilder.addClass(Config.class, config);
        TagBuilder.addClass(JavaConfig.class, config.java);
        TagBuilder.addClass(MinecraftConfig.class, config.minecraft);
        TagBuilder.addClass(MinecraftDirectoriesConfig.class, config.minecraft.directories);
        TagBuilder.addClass(MinecraftParametersConfig.class, config.minecraft.parameters);
        TagBuilder.addClass(ApplicationConfig.class, config.application);
        TagBuilder.rebuild();

        // nacte knihovny z adresare podle nacteneho konfiguracniho souboru a soubor nize opet ulozi
        config.minecraft.autoLoadedLibs = Minecraft.loadLibraries(config.minecraft);
        returnConfig.minecraft.autoLoadedLibs = config.minecraft.autoLoadedLibs;

        if (!Config.save(Minecraft.CONFIG_FILE, returnConfig)) {
            exit();
        }

        // odebere vsechny soubory s logy v adresari s hrou
        if (config.application.removeLogs) {
            LogsRemover.start(config.minecraft.directories.game);
        }

        // spusti minecraft
        MinecraftRunner mcRunner = new MinecraftRunner(config);
        mcRunner.start();

        if (config.application.showConsole) {
            mcRunner.join();
        } else {
            Thread.sleep(1000); // prodleva pro dokonceni zapisu do logu a uzavreni logu
        }
        exit();
    }

    private static void exit()
    {
        Logger.close();
        System.exit(0);
    }
}
