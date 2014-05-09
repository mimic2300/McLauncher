package cz.mimic.mclauncher.minecraft;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.mimic.mclauncher.config.MinecraftConfig;

/**
 * Podporovane verze minecraftu.
 * 
 * @author mimic
 */
public enum MinecraftVersion
{
//    MC_1_7_2("1.7.2") {
//
//        @Override
//        public void applyVersionChanges(ConfigMinecraft minecraft)
//        {
//            minecraft.directories.assets = "{mc::dir::game}\\assets";
//
//            minecraft.parameters.version = "1.7.2";
//            minecraft.parameters.accessToken = "1337535510N";
//        }
//    },
//    MC_1_7_2_FORGE_10_12_1_1061("1.7.2 Forge 10.12.1.1061") {
//
//        @Override
//        public void applyVersionChanges(ConfigMinecraft minecraft)
//        {
//            minecraft.mainClass = "net.minecraft.launchwrapper.Launch";
//
//            minecraft.directories.assets = "{mc::dir::game}\\assets\\virtual\\legacy";
//
//            minecraft.parameters.tweakClass = "cpw.mods.fml.common.launcher.FMLTweaker";
//            minecraft.parameters.version = "1.7.2-Forge10.12.1.1061";
//            minecraft.parameters.accessToken = "1337535510N";
//        }
//    },
    MC_1_6_4_FORGE_11_1_965("1.6.4 Forge 11.1.965") {

        @Override
        public void applyVersionChanges(MinecraftConfig minecraft)
        {
            minecraft.mainClass = "net.minecraft.launchwrapper.Launch";

            minecraft.directories.assets = "{mc::dir::game}\\assets\\virtual\\legacy";

            minecraft.parameters.tweakClass = "cpw.mods.fml.common.launcher.FMLTweaker";
            minecraft.parameters.version = "1.6.4-Forge9.11.1.965";
            minecraft.parameters.session = "1337535510N";
        }
    };

    private static final VersionComparator COMPARATOR = new VersionComparator();

    private String alias;

    private MinecraftVersion(String alias)
    {
        this.alias = alias;
    }

    /**
     * Ziska alias verze (citelny tvar).
     * 
     * @return
     */
    public String alias()
    {
        return alias;
    }

    /**
     * Upravi konfiguraci minecraftu podle verze.
     * 
     * @param minecraft
     */
    public abstract void applyVersionChanges(MinecraftConfig minecraft);

    @Override
    public String toString()
    {
        return alias;
    }

    /**
     * Ziska serazene verze.
     * 
     * @return
     */
    public static List<MinecraftVersion> sortedVersions()
    {
        List<MinecraftVersion> versions = Arrays.asList(values());
        Collections.sort(versions, COMPARATOR);
        return versions;
    }

    private static class VersionComparator implements Comparator<MinecraftVersion>
    {
        @Override
        public int compare(MinecraftVersion v1, MinecraftVersion v2)
        {
            // novejsi verze ma prednost
            return v2.alias.compareTo(v1.alias);
        }
    }
}
