package cz.mimic.mclauncher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cz.mimic.mclauncher.config.Config;

public class Minecraft implements Runnable
{
    private static final String BAT_EXTENSION = "bat";
    private static final String JAR_EXTENSION = "jar";

    private final Config config;
    private final StringBuilder sb = new StringBuilder();

    public Minecraft(Config config)
    {
        if (config == null) {
            throw new NullPointerException();
        }
        this.config = config;

        buildBatContent();
    }

    @Override
    public void run()
    {
        String bat = sb.toString();

        try {
            File temp = File.createTempFile(config.application.uniqueTitle, "." + BAT_EXTENSION);
            Files.write(Paths.get(temp.getAbsolutePath()), bat.getBytes());

            Runtime.getRuntime().exec(String.format(config.application.runFormat,
                    config.application.uniqueTitle,
                    temp.getAbsolutePath()));
            Runtime.getRuntime().exec(String.format(config.application.closeFormat, config.application.uniqueTitle));

            try {
                Thread.sleep(Integer.valueOf(config.application.delayBeforeDelete));
                Files.delete(Paths.get(temp.getAbsolutePath()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> loadLibs(String libDirectory)
    {
        File root = new File(libDirectory);
        Stack<File> stack = new Stack<File>();
        List<String> libs = new ArrayList<String>();

        if (root.exists()) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            File popFile = stack.pop();

            for (File file : popFile.listFiles()) {
                if (file.isDirectory()) {
                    stack.push(file);
                } else {
                    if (file.getName().endsWith("." + JAR_EXTENSION)) {
                        libs.add(file.getAbsolutePath());
                    }
                }
            }
        }
        return libs;
    }

    private void buildBatContent()
    {
        // sb.append("@echo off & ");
        sb.append("cd \"").append(config.minecraft.gameDir).append("\" & ");
        sb.append("\"").append(config.java.javawDir).append("\"");
        sb.append(" -XX:HeapDumpPath=")
                .append("MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
        sb.append(" -Xms").append(config.java.xms);
        sb.append(" -Xmx").append(config.java.xmx);
        sb.append(" ").append(config.java.getCustomParametersString());
        sb.append(" -Djava.library.path=\"").append(config.minecraft.nativesDir).append("\"");
        sb.append(" -cp \"").append(config.minecraft.getAllLibsString()).append("\"");
        sb.append(" ").append(config.minecraft.mainClass);
        sb.append(" --username ").append(config.minecraft.username);
        sb.append(" --version ").append(config.minecraft.version);
        sb.append(" --gameDir \"").append(config.minecraft.gameDir).append("\"");
        sb.append(" --assetsDir \"").append(config.minecraft.assetsDir).append("\"");
        sb.append(" --accessToken ").append(config.minecraft.accessToken);

        if (config.minecraft.assetIndex != null) {
            sb.append(" --assetIndex ").append(config.minecraft.assetIndex);
        }
        if (config.minecraft.uuid != null) {
            sb.append(" --uuid ").append(config.minecraft.uuid);
        }
        sb.append(" --userProperties {} ");

        if (config.minecraft.customParameters != null && !config.minecraft.customParameters.isEmpty()) {
            sb.append(config.minecraft.getCustomParametersString());
        }
    }
}
