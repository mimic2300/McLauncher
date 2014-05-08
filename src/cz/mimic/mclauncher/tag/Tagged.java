package cz.mimic.mclauncher.tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Oznaci tridy jako tagged a tim umozni vyhledavani tagu ve tride. Pokud trida bude obsahovat instancni promenne, ktere
 * budou oznaceny tagem, tak je nutne oznacit tridu jako tagged.
 * 
 * @author mimic
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Tagged
{
    /**
     * Slouzi jako prefix pred tagem instancni promenne a spolecne by meli tvorit unikatni tag.
     * 
     * @return
     */
    String value() default "";
}
