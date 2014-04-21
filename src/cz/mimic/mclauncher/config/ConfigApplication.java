package cz.mimic.mclauncher.config;

import com.google.gson.annotations.SerializedName;

public final class ConfigApplication
{
    @SerializedName("run_format")
    public String runFormat = "cmd /k start \"%s\" /min %s";

    @SerializedName("close_format")
    public String closeFormat = "taskkill /FI \"WINDOWTITLE eq %s*\"";

    @SerializedName("delay_before_delete")
    public String delayBeforeDelete = "3000";

    @SerializedName("unique_title")
    public String uniqueTitle = "17777d4fc9b804df4755d024b0f6860f";
}
