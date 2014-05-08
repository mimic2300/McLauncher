package cz.mimic.mclauncher.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Strategie pro vytvareni Json souboru.
 * 
 * @author mimic
 */
public class JsonSaveStrategy implements ExclusionStrategy
{
    /*
     * (non-Javadoc)
     * @see com.google.gson.ExclusionStrategy#shouldSkipClass(java.lang.Class)
     */
    @Override
    public boolean shouldSkipClass(Class<?> clazz)
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.google.gson.ExclusionStrategy#shouldSkipField(com.google.gson.FieldAttributes)
     */
    @Override
    public boolean shouldSkipField(FieldAttributes attr)
    {
        return attr.getAnnotation(Hide.class) != null;
    }
}
