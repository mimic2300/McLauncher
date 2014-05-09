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
public final class JavaConfig
{
    @Tag("path")
    public String path;

    @Tag("xms")
    @SerializedName("initialMemory")
    public String xms;

    @Tag("xmx")
    @SerializedName("maxMemory")
    public String xmx;

    @Tag("params")
    @SerializedName("params")
    public List<String> parameters;

    /**
     * Vytvori instanci konfigurace javy.
     */
    public JavaConfig()
    {
        path = "%programfiles%\\Java\\jre8\\bin\\java.exe"; // pro windows
        xms = "512M";
        xmx = "2G";
        parameters = null;
    }

    /**
     * Vytvori kopii konfigurace javy.
     * 
     * @param c
     */
    public JavaConfig(JavaConfig c)
    {
        path = c.path;
        xms = c.xms;
        xmx = c.xmx;

        if (c.parameters != null) {
            parameters = new ArrayList<String>(c.parameters);
        }
    }

    /**
     * Ziska vsechny parametry v podobe stringu.
     * 
     * @return
     */
    public String getParametersString()
    {
        StringBuilder sb = new StringBuilder();

        if (parameters != null) {
            for (String param : parameters) {
                sb.append(param).append(" ");
            }
        }
        return sb.toString();
    }
}
