package cz.mimic.mclauncher.config;

import com.google.gson.annotations.SerializedName;

public final class ConfigApplication
{
    @SerializedName("run_format")
    public String runFormat;

    @SerializedName("close_format")
    public String closeFormat;

    @SerializedName("delay_before_delete")
    public String delayBeforeDelete;

    @SerializedName("unique_title")
    public String uniqueTitle;
}
