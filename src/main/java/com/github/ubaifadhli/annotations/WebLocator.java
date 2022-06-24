package com.github.ubaifadhli.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to store locators of elements for Web platform.
 *
 * @author Fadhli Ubai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WebLocator {

    /**
     * Stores XPath locator of element for Web platform.
     */
    String xpath() default "";

    /**
     * Stores ID locator of element for Web platform.
     */
    String id() default "";
}
