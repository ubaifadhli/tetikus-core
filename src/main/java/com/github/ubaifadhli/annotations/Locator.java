package com.github.ubaifadhli.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Locator {
    // General locator if both Web and Mobile has same locator.
    String xpath() default "";
    String id() default "";

    // Locator for Web platform.
    String webXPath() default "";
    String webID() default "";

    // Locator for Mobile platform
    String mobileXPath() default "";
    String mobileID() default "";
    String mobileAccessibilityID() default "";
}
