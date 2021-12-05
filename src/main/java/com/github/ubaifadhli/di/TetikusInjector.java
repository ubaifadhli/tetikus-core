package com.github.ubaifadhli.di;

import com.github.ubaifadhli.annotations.BaseURL;
import com.github.ubaifadhli.annotations.Locator;
import com.github.ubaifadhli.annotations.MobileLocator;
import com.github.ubaifadhli.annotations.WebLocator;
import com.github.ubaifadhli.page.PageObject;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import com.github.ubaifadhli.util.Element;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class TetikusInjector {
    public static void startInject(Object currentObject) {
        List<Field> currentObjectFields = Arrays.asList(currentObject.getClass().getDeclaredFields());

        currentObjectFields.forEach(field -> {
            System.out.println(field.getName());
            System.out.println(field.getType());

            if (field.getType().getSuperclass() == PageObject.class) {
                BaseURL baseURL = field.getType().getAnnotation(BaseURL.class);

                Class<?> pageObjectSubclass = field.getType();

                Object createdPageObject = null;

                try {
                    createdPageObject = pageObjectSubclass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (baseURL != null) {
                    try {
                        Field baseURLField = createdPageObject.getClass().getSuperclass().getDeclaredField("baseURL");

                        baseURLField.setAccessible(true);
                        baseURLField.set(createdPageObject, baseURL.value());

                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }

                field.setAccessible(true);

                try {
                    field.set(currentObject, createdPageObject);

                } catch (IllegalAccessException e) {
                    // Error : Failed to inject PageObject instance.
                }

                // Recursive injecting here.
                startInject(createdPageObject);
            }

            if (field.getType() == Element.class) {
                boolean fieldHasRequiredAnnotations =
                        field.isAnnotationPresent(Locator.class) ||
                                field.isAnnotationPresent(WebLocator.class) ||
                                field.isAnnotationPresent(MobileLocator.class);

                if (fieldHasRequiredAnnotations) {
                    Locator locator = field.getAnnotation(Locator.class);
                    WebLocator webLocator = field.getAnnotation(WebLocator.class);
                    MobileLocator mobileLocator = field.getAnnotation(MobileLocator.class);

                    Element.ElementBuilder builder = Element.builder();

                    if (locator != null) {
                        if (locator.webID().length() > 0)
                            builder.webLocator(By.id(locator.webID()));

                        if (locator.webXPath().length() > 0)
                            builder.webLocator(By.xpath(locator.webXPath()));

                        if (locator.mobileID().length() > 0)
                            builder.mobileLocator(MobileBy.AccessibilityId(locator.mobileID()));

                        if (locator.mobileXPath().length() > 0)
                            builder.mobileLocator(MobileBy.xpath(locator.mobileXPath()));
                    }

                    if (webLocator != null) {
                        if (webLocator.id().length() > 0)
                            builder.webLocator(By.id(webLocator.id()));

                        if (webLocator.xpath().length() > 0)
                            builder.webLocator(By.xpath(webLocator.xpath()));
                    }

                    if (mobileLocator != null) {
                        if (mobileLocator.id().length() > 0)
                            builder.mobileLocator(By.id(mobileLocator.id()));

                        if (mobileLocator.xpath().length() > 0)
                            builder.mobileLocator(By.xpath(mobileLocator.xpath()));
                    }

                    Element createdElement = builder.build();

                    field.setAccessible(true);

                    try {
                        field.set(currentObject, createdElement);

                    } catch (IllegalAccessException e) {
                        // Error : Failed to inject Element instance.
                    }

                } else {
                    // Error : Element does not have locator.
                }
            }
        });
    }
}
