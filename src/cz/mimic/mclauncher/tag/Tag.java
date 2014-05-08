package cz.mimic.mclauncher.tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Oznaci instancni promenne jako tag a tim umozni jejich pridani do seznamu tagu pri nacteni Json.
 * 
 * @author mimic
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Tag
{
    /**
     * Mel by byt stejny jako nazev instancni promenne pro snazsi pouziti.
     * 
     * @return
     */
    String value();
}
