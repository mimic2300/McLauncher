package cz.mimic.mclauncher.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class ConfigLauncher
{
    @Expose
    @SerializedName("run_format")
    private String runFormat = "cmd /k start \"%s\" /min %s";

    @Expose
    @SerializedName("close_format")
    private String closeFormat = "taskkill /FI \"WINDOWTITLE eq %s*\"";

    @Expose
    @SerializedName("delay_before_delete")
    private int delayBeforeDelete = 3000;

    public String getRunFormat()
    {
        return runFormat;
    }

    public String getCloseFormat()
    {
        return closeFormat;
    }

    public int getDelayBeforeDelete()
    {
        return delayBeforeDelete;
    }
}
