package cz.mimic.mclauncher.config;

import com.google.gson.annotations.SerializedName;

import cz.mimic.mclauncher.alias.IgnoreAlias;

public final class Config
{
    @IgnoreAlias
    @SerializedName("application")
    public ConfigApplication application = new ConfigApplication();

    @IgnoreAlias
    @SerializedName("java")
    public ConfigJava java = new ConfigJava();

    @IgnoreAlias
    @SerializedName("minecraft")
    public ConfigMinecraft minecraft = new ConfigMinecraft();
}
