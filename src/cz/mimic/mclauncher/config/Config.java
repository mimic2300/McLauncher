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
    public ApplicationConfig application = new ApplicationConfig();

    @SerializedName("java")
    public JavaConfig java = new JavaConfig();

    @SerializedName("minecraft")
    public MinecraftConfig minecraft = new MinecraftConfig();

    /**
     * Vytvori instanci konfigurace.
     */
    public Config()
    {}

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
     * @param jsonFile
     * @param config
     * @return
     */
    public static boolean save(String jsonFile, Config config)
    {
        if (config == null) {
            LOGGER.error("save", "Konfigurace je NULL");
            return false;
        }
        try (FileWriter fw = new FileWriter(jsonFile)) {
            String json = Json.JSON_SAVE_PRETTY_INSTANCE.toJson(config);
            fw.write(json);
            LOGGER.info("save", "Konfigurace byla ulozena do %s", jsonFile);
            return true;

        } catch (IOException e) {
            LOGGER.error("save", "Nastala chyba pri ukladani konfigurace do %s", jsonFile);
            LOGGER.debug(e);
            return false;
        }
    }

    /**
     * Nacte konfiguraci z Json souboru.
     * 
     * @param jsonFile
     * @return
     */
    public static Config load(String jsonFile)
    {
        Config config = null;

        if (!Files.exists(Paths.get(jsonFile))) {
            LOGGER.warning("load", "Konfiguracni soubor %s neexistuje", jsonFile);
            return config;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
            config = Json.JSON_LOAD_PRETTY_INSTANCE.fromJson(br, Config.class);
            LOGGER.info("load", "Konfiguracni soubor %s byl nacten", jsonFile);

        } catch (IOException e) {
            LOGGER.error("load", "Nastala chyba pri nacitani konfiguracniho souboru %s", jsonFile);
            LOGGER.debug(e);
        } catch (JsonParseException e) {
            LOGGER.error("load", "Json soubor %s neni validni", jsonFile);
        }
        return config;
    }
}
