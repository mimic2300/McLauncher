package cz.mimic.mclauncher.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.mimic.mclauncher.config.Config;

public final class Json
{
    private static final Gson JSON_PRETTY_INSTANCE = new GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    private Json()
    {}

    public static void save(String jsonFile, Config config)
    {
        try (FileWriter fw = new FileWriter(jsonFile)) {
            String json = toJson(config);
            fw.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config load(String jsonFile)
    {
        if (!Files.exists(Paths.get(jsonFile))) {
            Message.error(String.format("Json config file: %s not found!", jsonFile));
            System.exit(1);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
            Config config = fromJson(br);
            return config;

        } catch (IOException e) {
            Message.error(e.getMessage());
            System.exit(1);
            return null;
        }
    }

    public static String toJson(Config config)
    {
        return JSON_PRETTY_INSTANCE.toJson(config);
    }

    public static Config fromJson(String jsonData)
    {
        return JSON_PRETTY_INSTANCE.fromJson(jsonData, Config.class);
    }

    public static Config fromJson(Reader reader)
    {
        return JSON_PRETTY_INSTANCE.fromJson(reader, Config.class);
    }
}
