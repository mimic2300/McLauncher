package cz.mimic.mclauncher.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import cz.mimic.mclauncher.json.Json;
import cz.mimic.mclauncher.logger.Logger;

/**
 * Hlavni konfigurace, ktera se pouziva pro Json.
 * 
 * @author mimic
 */
public final class Config
{
    private static final Logger LOGGER = new Logger(Config.class);

    @SerializedName("application")
    public ApplicationConfig application;

    @SerializedName("java")
    public JavaConfig java;

    @SerializedName("minecraft")
    public MinecraftConfig minecraft;

    /**
     * Vytvori instanci konfigurace.
     */
    public Config()
    {
        application = new ApplicationConfig();
        java = new JavaConfig();
        minecraft = new MinecraftConfig();
    }

    /**
     * Vytvori kopii konfigurace.
     * 
     * @param c
     */
    public Config(final Config c)
    {
        application = new ApplicationConfig(c.application);
        java = new JavaConfig(c.java);
        minecraft = new MinecraftConfig(c.minecraft);
    }

    /**
     * Ulozi konfiguraci do souboru v Json.
     * 
     * @param configFile
     * @param config
     * @return
     */
    public static boolean save(String configFile, Config config)
    {
        if (config == null) {
            LOGGER.error("save", "Konfigurace je NULL");
            return false;
        }
        try (FileWriter fw = new FileWriter(configFile)) {
            String json = Json.JSON_SAVE_PRETTY_INSTANCE.toJson(config);
            fw.write(json);
            LOGGER.info("save", "Konfigurace byla ulozena do %s", configFile);
            return true;

        } catch (IOException e) {
            LOGGER.error("save", "Nastala chyba pri ukladani konfigurace do %s", configFile);
            LOGGER.debug(e);
            return false;
        }
    }

    /**
     * Nacte konfiguraci z Json souboru.
     * 
     * @param configFile
     * @return
     */
    public static Config load(String configFile)
    {
        Config config = null;

        if (!Files.exists(Paths.get(configFile))) {
            LOGGER.warning("load", "Konfiguracni soubor %s neexistuje", configFile);
            return config;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            config = Json.JSON_LOAD_PRETTY_INSTANCE.fromJson(br, Config.class);
            LOGGER.info("load", "Konfiguracni soubor %s byl nacten", configFile);

        } catch (IOException e) {
            LOGGER.error("load", "Nastala chyba pri nacitani konfiguracniho souboru %s", configFile);
            LOGGER.debug(e);
        } catch (JsonParseException e) {
            LOGGER.error("load", "Json soubor %s neni validni", configFile);
        }
        return config;
    }
}
