package cz.mimic.mclauncher.config;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public final class ConfigJava
{
    @SerializedName("javaw_dir")
    public String javawDir = "C:\\Program Files\\Java\\jdk1.8.0\\bin\\javaw.exe";

    @SerializedName("initial_memory")
    public String xms = "512M";

    @SerializedName("max_memory")
    public String xmx = "2G";

    @SerializedName("java_custom_parameters")
    public List<String> customParameters = new ArrayList<String>();

    public String getCustomParametersString()
    {
        StringBuilder sb = new StringBuilder();

        for (String param : customParameters) {
            sb.append(param).append(" ");
        }
        return sb.toString();
    }
}
