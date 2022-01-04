package com.github.ubaifadhli.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MobileLocator {
    String xpath() default "";
    String id() default "";
    String accessibilityID() default "";
    int[] coordinate() default -1;
}
