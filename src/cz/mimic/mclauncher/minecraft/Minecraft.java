package cz.mimic.mclauncher.minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cz.mimic.mclauncher.config.Config;
import cz.mimic.mclauncher.config.MinecraftConfig;
import cz.mimic.mclauncher.logger.Logger;
import cz.mimic.mclauncher.util.Util;

/**
 * Spravuje veskere nastaveni pro runner minecraftu.
 * 
 * @author mimic
 */
public final class Minecraft
{
    private static final Logger LOGGER = new Logger(Minecraft.class);

    public static final String CONFIG_FILE = "mcLauncher.json";

    private Minecraft()
    {}

    /**
     * Vyhleda vsechny knihovny a prida do seznamu.
     * 
     * @param librariesDirectory
     * @return
     */
    public static List<String> loadLibraries(MinecraftConfig minecraft)
    {
        if (minecraft.directories.libraries == null) {
            LOGGER.error("loadLibraries", "Adresar s knihovnami %d neexistuje", minecraft.directories.libraries);
            return null;
        }
        File root = new File(minecraft.directories.libraries);
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
                    if (file.getName().endsWith(".jar")) {
                        libs.add(file.getAbsolutePath());
                    }
                }
            }
        }
        // prida knihovnu pro samotny minecraft
        libs.add(getMinecraftJar(minecraft));
        return libs;
    }

    /**
     * Vytvori obsah bat souboru.
     * 
     * @param directories
     * @return
     */
    public static String createBatFileContent(Config config)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("@echo off & ");
        sb.append("cd \"").append(config.minecraft.directories.game).append("\" & ");
        sb.append(createJavaCommand(config));

        return sb.toString();
    }

    /**
     * Ziska soubor s minecraftem.
     * 
     * @param minecraft
     * @return
     */
    public static String getMinecraftJar(MinecraftConfig minecraft)
    {
        File file = new File(String.format("%s\\versions\\%s\\%s.jar",
                minecraft.directories.game,
                minecraft.parameters.version,
                minecraft.parameters.version));

        if (!file.exists()) {
            LOGGER.error("getMinecraftJar", "Minecraft java soubor %s neexistuje", file.getAbsolutePath());
        }
        return file.getAbsolutePath();
    }

    private static String createJavaCommand(Config config)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("\"").append(config.java.path).append("\"");
        sb.append(" -XX:HeapDumpPath=")
                .append("MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
        sb.append(" -Xms").append(config.java.xms);
        sb.append(" -Xmx").append(config.java.xmx);
        sb.append(" ").append(config.java.parametersString());
        sb.append(" -Djava.library.path=\"").append(config.minecraft.directories.natives).append("\"");
        sb.append(" -cp \"").append(config.minecraft.getAllLibsString()).append("\"");

        sb.append(" ").append(config.minecraft.mainClass).append(" ");
        sb.append(Util.getParametersString(config.minecraft.parameters));
        sb.append(Util.getParametersString(config.minecraft.directories));

        return sb.toString();
    }
}
