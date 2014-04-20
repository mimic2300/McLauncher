package cz.mimic.mclauncher.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cz.mimic.mclauncher.base.Alias;

public final class Config
{
    private static final Pattern REGEX_ALIAS_PATTERN = Pattern.compile("(\\{)(.*?)(\\})");
    private static final Map<String, String> ALIAS_MAP;

    static {
        final Field[] fields = Config.class.getDeclaredFields();

        ALIAS_MAP = new HashMap<String, String>();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.isAnnotationPresent(Alias.class)) {
                Alias alias = field.getAnnotation(Alias.class);
                ALIAS_MAP.put(alias.name(), field.getName());
            }
        }
    }

    /* =========================================================================================================== */

    @SerializedName("launcher")
    private ConfigLauncher launcher = new ConfigLauncher(); // hide in the Json

    @Expose
    @Alias(name = "JAVAW")
    @SerializedName("javaw")
    private String javaPath = "%programfiles%\\Java\\jre7\\bin\\javaw.exe";

    @Expose
    @Alias(name = "INIT_MEM")
    @SerializedName("xms")
    private String initMemory = "1G";

    @Expose
    @Alias(name = "MAX_MEM")
    @SerializedName("xmx")
    private String maxMemory = "2G";

    @Expose
    @Alias(name = "MINECRAFT")
    @SerializedName("minecraft")
    private String gameDirectory = "%appdata%\\.minecraft";

    @Expose
    @Alias(name = "ASSETS")
    @SerializedName("assets")
    private String assetsDir = "{MINECRAFT}\\assets";

    @Expose
    @Alias(name = "NATIVES")
    @SerializedName("natives")
    private String nativesDir = "{MINECRAFT}\\versions\\{VERSION}\\{VERSION}-natives";

    @Expose
    @Alias(name = "MAIN_CLASS")
    @SerializedName("main_class")
    private String mainClass = "net.minecraft.client.main.Main";

    @Expose
    @Alias(name = "TWEAK_CLASS")
    @SerializedName("tweak_class")
    private String tweakClass = "cpw.mods.fml.common.launcher.FMLTweaker";

    @Expose
    @Alias(name = "LOGIN")
    @SerializedName("login")
    private String login = "mimic";

    @Expose
    @Alias(name = "VERSION")
    @SerializedName("version")
    private String version = "1.7.9";

    @Expose
    @Alias(name = "ASSET_INDEX")
    @SerializedName("asset_index")
    private String assetIndex = "1.7.4";

    @Expose
    @Alias(name = "TOKEN")
    @SerializedName("access_token")
    private String accessToken = "1337535510N";

    @Expose
    @Alias(name = "LIBS")
    @SerializedName("libs")
    private String[] libs = {
            "{MINECRAFT}\\libraries\\java3d\\vecmath\\1.3.1\\vecmath-1.3.1.jar",
            "{MINECRAFT}\\libraries\\net\\sf\\trove4j\\trove4j\\3.0.3\\trove4j-3.0.3.jar",
            "{MINECRAFT}\\libraries\\com\\ibm\\icu\\icu4j-core-mojang\\51.2\\icu4j-core-mojang-51.2.jar",
            "{MINECRAFT}\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\4.5\\jopt-simple-4.5.jar",
            "{MINECRAFT}\\libraries\\com\\paulscode\\codecjorbis\\20101023\\codecjorbis-20101023.jar",
            "{MINECRAFT}\\libraries\\com\\paulscode\\codecwav\\20101023\\codecwav-20101023.jar",
            "{MINECRAFT}\\libraries\\com\\paulscode\\libraryjavasound\\20101123\\libraryjavasound-20101123.jar",
            "{MINECRAFT}\\libraries\\com\\paulscode\\librarylwjglopenal\\20100824\\librarylwjglopenal-20100824.jar",
            "{MINECRAFT}\\libraries\\com\\paulscode\\soundsystem\\20120107\\soundsystem-20120107.jar",
            "{MINECRAFT}\\libraries\\io\\netty\\netty-all\\4.0.10.Final\\netty-all-4.0.10.Final.jar",
            "{MINECRAFT}\\libraries\\com\\google\\guava\\guava\\15.0\\guava-15.0.jar",
            "{MINECRAFT}\\libraries\\org\\apache\\commons\\commons-lang3\\3.1\\commons-lang3-3.1.jar",
            "{MINECRAFT}\\libraries\\commons-io\\commons-io\\2.4\\commons-io-2.4.jar",
            "{MINECRAFT}\\libraries\\commons-codec\\commons-codec\\1.9\\commons-codec-1.9.jar",
            "{MINECRAFT}\\libraries\\net\\java\\jinput\\jinput\\2.0.5\\jinput-2.0.5.jar",
            "{MINECRAFT}\\libraries\\net\\java\\jutils\\jutils\\1.0.0\\jutils-1.0.0.jar",
            "{MINECRAFT}\\libraries\\com\\google\\code\\gson\\gson\\2.2.4\\gson-2.2.4.jar",
            "{MINECRAFT}\\libraries\\com\\mojang\\authlib\\1.5.13\\authlib-1.5.13.jar",
            "{MINECRAFT}\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.0-beta9\\log4j-api-2.0-beta9.jar",
            "{MINECRAFT}\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.0-beta9\\log4j-core-2.0-beta9.jar",
            "{MINECRAFT}\\libraries\\org\\lwjgl\\lwjgl\\lwjgl\\2.9.1\\lwjgl-2.9.1.jar",
            "{MINECRAFT}\\libraries\\org\\lwjgl\\lwjgl\\lwjgl_util\\2.9.1\\lwjgl_util-2.9.1.jar",
            "{MINECRAFT}\\libraries\\tv\\twitch\\twitch\\5.16\\twitch-5.16.jar",
            "{MINECRAFT}\\versions\\{VERSION}\\{VERSION}.jar" };

    /* =========================================================================================================== */

    public ConfigLauncher getLauncher()
    {
        return launcher;
    }

    public String getJavaPath()
    {
        return translate(javaPath);
    }

    public String getInitMemory()
    {
        return translate(initMemory);
    }

    public String getMaxMemory()
    {
        return translate(maxMemory);
    }

    public String getGameDirectory()
    {
        return translate(gameDirectory);
    }

    public String getMainClass()
    {
        return translate(mainClass);
    }

    public String getTweakClass()
    {
        return translate(tweakClass);
    }

    public String getLogin()
    {
        return translate(login);
    }

    public String getVersion()
    {
        return translate(version);
    }

    public String getAssetIndex()
    {
        return assetIndex;
    }

    public String getAccessToken()
    {
        return translate(accessToken);
    }

    public String getNativesDir()
    {
        return translate(nativesDir);
    }

    public String getAssetsDir()
    {
        return translate(assetsDir);
    }

    public String getCpLibs()
    {
        StringBuilder sb = new StringBuilder();

        for (String lib : libs) {
            sb.append(lib).append(";");
        }
        String libsString = sb.toString();

        if (libsString.endsWith(";")) {
            libsString = libsString.substring(0, libsString.length() - 1);
        }
        return translate(libsString);
    }

    /* =========================================================================================================== */

    private String translate(String fieldString)
    {
        final Matcher matcher = REGEX_ALIAS_PATTERN.matcher(fieldString);

        while (matcher.find()) {
            String group = matcher.group(2);
            String value = getValue(this, ALIAS_MAP.get(group));

            if (value != null) {
                fieldString = fieldString.replace(matcher.group(1) + group + matcher.group(3), value);
            }
        }
        return (fieldString == null) ? "" : fieldString;
    }

    private String getValue(Config config, String fieldName)
    {
        final Field[] fields = Config.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                try {
                    return field.get(config).toString();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
