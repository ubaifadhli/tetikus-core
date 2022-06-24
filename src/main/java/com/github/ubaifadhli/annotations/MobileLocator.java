package com.github.ubaifadhli.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to store locators of elements for Mobile platform.
 *
 * @author Fadhli Ubai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MobileLocator {

    /**
     * Stores XPath locator of element for Mobile platform.
     */
    String xpath() default "";

    /**
     * Stores ID locator of element for Mobile platform.
     */
    String id() default "";

    /**
     * Stores accessibility ID locator of element for Mobile platform.
     */
    String accessibilityID() default "";

    /**
     * Stores coordinate of element for Mobile platform.
     * Only accepts 2 items in array, with first item counted as X coordinate
     * and the second one as Y coordinate.
     */
    int[] coordinate() default -1;
}
