package cz.mimic.mclauncher.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import cz.mimic.mclauncher.config.Config;

public class Minecraft implements Runnable
{
    private final Config config;
    private final StringBuilder sb;

    public Minecraft(Config config)
    {
        this.config = config;
        sb = new StringBuilder();

        sb.append("@echo off & ");
        sb.append("cd ").append(config.getGameDirectory()).append(" & ");
        sb.append("\"").append(config.getJavaPath()).append("\"");
        sb.append(" -XX:HeapDumpPath=")
                .append("MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
        sb.append(" -Xms").append(config.getInitMemory());
        sb.append(" -Xmx").append(config.getMaxMemory());
        sb.append(" -Djava.library.path=\"").append(config.getNativesDir()).append("\"");
        sb.append(" -cp \"").append(config.getCpLibs()).append("\"");
        sb.append(" ").append(config.getMainClass());
        sb.append(" --username ").append(config.getLogin());
        sb.append(" --version ").append(config.getVersion());
        sb.append(" --gameDir \"").append(config.getGameDirectory()).append("\"");
        sb.append(" --assetsDir \"").append(config.getAssetsDir()).append("\"");

        if (config.getAssetIndex() != null) {
            sb.append(" --assetIndex ").append(config.getAssetIndex());
        }
        if (config.getAccessToken() != null) {
            sb.append(" --accessToken ").append(config.getAccessToken());
        }

        sb.append(" --userProperties {}");

        if (config.getTweakClass() != null) {
            sb.append(" --tweakClass ").append(config.getTweakClass());
        }
    }

    public String getBatFile()
    {
        return sb.toString();
    }

    @Override
    public void run()
    {
        String bat = getBatFile();
        String randTitle = String.valueOf(new Random().nextInt());

        try {
            File temp = File.createTempFile(randTitle, ".bat");
            Files.write(Paths.get(temp.getAbsolutePath()), bat.getBytes());

            Runtime.getRuntime().exec(String.format(config.getLauncher().getRunFormat(),
                    randTitle,
                    temp.getAbsolutePath()));
            Runtime.getRuntime().exec(String.format(config.getLauncher().getCloseFormat(), randTitle));

            try {
                Thread.sleep(config.getLauncher().getDelayBeforeDelete());
                Files.delete(Paths.get(temp.getAbsolutePath()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
