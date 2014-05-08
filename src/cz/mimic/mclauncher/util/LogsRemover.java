package cz.mimic.mclauncher.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.mimic.mclauncher.logger.Logger;

/**
 * Odebere vsechny soubory s logy.
 * 
 * @author mimic
 */
public class LogsRemover
{
    private static final Logger LOGGER = new Logger(LogsRemover.class);
    private static final Pattern PATTERN = Pattern.compile("^(?:(.+log\\.\\d{1,}\\.lck)|(.+\\.log)|(.+log\\.\\d{1,})|(.+\\.log\\.lck))$");

    /**
     * Spusti odebrani souboru s logy.
     * 
     * @param directory
     */
    public static void start(String directory)
    {
        File root = new File(Paths.get(directory).toFile().getAbsolutePath());
        File[] files = root.listFiles();

        for (File f : files) {
            Matcher m = PATTERN.matcher(f.getName());

            if (m.find()) {
                try {
                    f.delete();
                    LOGGER.info("start", "Log %s byl vymazan", f.getName());
                } finally {
                }
            }
        }
    }
}
