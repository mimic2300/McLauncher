package cz.mimic.mclauncher.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pri ulozeni konfigurace do Json skryje vsechny hodnoty, ktery budou oznaceny jako hide.
 * 
 * @author mimic
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Hide
{}
