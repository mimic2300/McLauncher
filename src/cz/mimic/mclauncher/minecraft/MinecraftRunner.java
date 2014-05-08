package cz.mimic.mclauncher.minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.logger.Logger;

/**
 * Proces pro spusteni minecraftu.
 * 
 * @author mimic
 */
public class MinecraftRunner implements Runnable
{
    private static final Logger LOGGER = new Logger(MinecraftRunner.class);

    private Config config;

    /**
     * Vytvori instanci procesu.
     * 
     * @param config
     */
    public MinecraftRunner(Config config)
    {
        this.config = config;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        String batContent = Minecraft.createBatFileContent(config);

        try {
            if (config.application.uniqueTitle == null) {
                LOGGER.error("run", "Aplikace nema vyplneny unikatni titulek");
                return;
            }
            if (config.application.runFormat == null) {
                LOGGER.error("run", "Format pro spusteni neni vyplneny");
                return;
            }
            if (config.application.closeFormat == null) {
                LOGGER.error("run", "Format pro ukonceni neni vyplneny");
                return;
            }
            if (config.application.delayBeforeDelete == null) {
                LOGGER.error("run", "Prodleva pred smazanim tempu neni vyplnena");
                return;
            }
            try {
                Integer.parseInt(config.application.delayBeforeDelete);

            } catch (NumberFormatException e) {
                LOGGER.error("run", "Prodleva %s neni cislo", config.application.delayBeforeDelete);
                return;
            }
            File temp = File.createTempFile(config.application.uniqueTitle, ".bat");
            Files.write(Paths.get(temp.getAbsolutePath()), batContent.getBytes());

            Runtime.getRuntime().exec(String.format(config.application.runFormat,
                    config.application.uniqueTitle,
                    temp.getAbsolutePath()));
            Runtime.getRuntime().exec(String.format(config.application.closeFormat, config.application.uniqueTitle));

            try {
                Thread.sleep(Integer.valueOf(config.application.delayBeforeDelete));
                Files.delete(Paths.get(temp.getAbsolutePath()));

            } catch (InterruptedException e) {
                LOGGER.error("run", "Nastala chyba v preruseni vlakna");
                e.printStackTrace();
            }
        } catch (IOException e) {
            LOGGER.error("run", "Nastala chyba pri vytvareni souboru pro spusteni minecraftu");
            e.printStackTrace();
        }
    }
}
