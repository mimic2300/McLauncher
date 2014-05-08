package cz.mimic.mclauncher.config;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import cz.mimic.mclauncher.tag.Tag;
import cz.mimic.mclauncher.tag.Tagged;

/**
 * Konfigurace pro javu.
 * 
 * @author mimic
 */
@Tagged("java")
public final class ConfigJava
{
    @Tag("javawDir")
    public String javawDir;

    @Tag("xms")
    @SerializedName("initialMemory")
    public String xms;

    @Tag("xmx")
    @SerializedName("maxMemory")
    public String xmx;

    @Tag("params")
    public List<String> params;

    /**
     * Vytvori instanci konfigurace javy.
     */
    public ConfigJava()
    {}

    /**
     * Vytvori kopii konfigurace javy.
     * 
     * @param c
     */
    public ConfigJava(ConfigJava c)
    {
        javawDir = c.javawDir;
        xms = c.xms;
        xmx = c.xmx;

        if (c.params != null) {
            params = new ArrayList<String>(c.params);
        }
    }

    /**
     * Ziska vsechny parametry v podobe stringu.
     * 
     * @return
     */
    public String parametersString()
    {
        StringBuilder sb = new StringBuilder();

        if (params != null) {
            for (String param : params) {
                sb.append(param).append(" ");
            }
        }
        return sb.toString();
    }
}
