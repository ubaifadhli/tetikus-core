package com.github.ubaifadhli.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to store locators of platform element.
 *
 * @author Fadhli Ubai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Locator {
    /**
     * Stores XPath locator of element for Web and Mobile platform.
     * Preferred when both platform has a same locator.
     */
    String xpath() default "";

    /**
     * Stores ID locator of element for Web and Mobile platform.
     * Preferred when both platform has a same locator.
     */
    String id() default "";

    /**
     * Stores XPath locator of element for Web platform.
     */
    String webXPath() default "";

    /**
     * Stores ID locator of element for Web platform.
     */
    String webID() default "";

    /**
     * Stores XPath locator of element for Mobile platform.
     */
    String mobileXPath() default "";

    /**
     * Stores ID locator of element for Mobile platform.
     */
    String mobileID() default "";

    /**
     * Stores accessibility ID locator of element for Mobile platform.
     */
    String mobileAccessibilityID() default "";

    /**
     * Stores coordinate of element for Mobile platform.
     * Only accepts 2 items in array, with first item counted as X coordinate
     * and the second one as Y coordinate.
     */
    int[] mobileCoordinate() default -1;
}
