package cz.mimic.mclauncher.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.annotations.SerializedName;

public final class ConfigMinecraft
{
    @SerializedName("game_dir")
    public String gameDir;

    @SerializedName("assets_dir")
    public String assetsDir;

    @SerializedName("libraries_dir")
    public String librariesDir;

    @SerializedName("natives_dir")
    public String nativesDir;

    @SerializedName("main_class")
    public String mainClass;

    @SerializedName("username")
    public String username;

    @SerializedName("version")
    public String version;

    @SerializedName("asset_index")
    public String assetIndex;

    @SerializedName("uuid")
    public String uuid;

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("minecraft_custom_parameters")
    public Map<String, String> customParameters = new HashMap<String, String>();

    @SerializedName("minecraft_custom_libs")
    public List<String> customLibs = new ArrayList<String>();

    // @Hide
    @SerializedName("libs")
    public List<String> libs = new ArrayList<String>();

    public String getCustomParametersString()
    {
        StringBuilder sb = new StringBuilder();

        for (Entry<String, String> entry : customParameters.entrySet()) {
            sb.append(String.format("--%s %s ", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

    public String getLibsString()
    {
        return prepareLibs(libs);
    }

    public String getCustomLibsString()
    {
        return prepareLibs(customLibs);
    }

    public String getAllLibsString()
    {
        return getLibsString() + getCustomLibsString();
    }

    private String prepareLibs(List<String> libs)
    {
        StringBuilder sb = new StringBuilder();

        for (String lib : libs) {
            sb.append(lib).append(";");
        }
        return sb.toString();
    }
}
