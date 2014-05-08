package cz.mimic.mclauncher.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Obsahuje pomocne funkce pro praci s Json.
 * 
 * @author mimic
 */
public final class Json
{
    /**
     * Instance Json pro vytvareni Json souboru.
     */
    public static final Gson JSON_SAVE_PRETTY_INSTANCE = new GsonBuilder().setPrettyPrinting()
            .setExclusionStrategies(new JsonSaveStrategy())
            .create();

    /**
     * Instance Json pro nacitani Json souboru.
     */
    public static final Gson JSON_LOAD_PRETTY_INSTANCE = new GsonBuilder().setPrettyPrinting()
            .setExclusionStrategies(new JsonLoadStrategy())
            .create();

    private Json()
    {}
}
