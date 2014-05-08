package cz.mimic.mclauncher.config.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Parametr pro minecraft.
 * 
 * @author mimic
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Parameter
{
    /**
     * Nazev parametru, ktery pouziva minecraft.
     * 
     * @return
     */
    String value();

    /**
     * Ma se hodnota parametru dat do uvozovek?
     * 
     * @return
     */
    boolean quotationMarks() default false;
}
