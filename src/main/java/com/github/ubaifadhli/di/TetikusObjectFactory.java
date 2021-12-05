package com.github.ubaifadhli.di;

import org.testng.IObjectFactory;
import org.testng.internal.ObjectFactoryImpl;

import java.lang.reflect.Constructor;

// This custom IObjectFactory class is needed because I need the instance of the test class.
public class TetikusObjectFactory implements IObjectFactory {
    private final ObjectFactoryImpl wrapped = new ObjectFactoryImpl();

    @Override
    public Object newInstance(Constructor constructor, Object... objects) {
        Object newObject = wrapped.newInstance(constructor, objects);
        TetikusInjector.startInject(newObject);

        return newObject;
    }
}
