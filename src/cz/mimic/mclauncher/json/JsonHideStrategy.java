package cz.mimic.mclauncher.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class JsonHideStrategy implements ExclusionStrategy
{
    @Override
    public boolean shouldSkipClass(Class<?> clazz)
    {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes attr)
    {
        return (attr.getAnnotation(Hide.class) != null);
    }
}
