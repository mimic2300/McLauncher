package cz.mimic.mclauncher.minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.logger.Logger;

/**
 * Proces pro spusteni minecraftu.
 * 
 * @author mimic
 */
public class MinecraftRunner extends Thread
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
        try {
            String command = Minecraft.createJavaCommand(config);
            Process proc = Runtime.getRuntime().exec(command);
            String line;

            LOGGER.info("run", "Minecraft se nyni spusti, pockejte prosim...%n");

            if (config.application.showConsole) {
                try (BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                    while ((line = input.readLine()) != null) {
                        if (isInterrupted()) {
                            break;
                        }
                        LOGGER.minecraft(line);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("run", "Nastala chyba pri vytvareni procesu minecraftu");
            e.printStackTrace();
        }
    }
}
