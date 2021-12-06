package com.github.ubaifadhli.runners;

import com.github.ubaifadhli.di.TetikusObjectFactory;
import com.github.ubaifadhli.listeners.TetikusListeners;
import org.testng.IObjectFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.ObjectFactory;

@Listeners({TetikusListeners.class})
public class TetikusBaseRunner {
    @ObjectFactory
    public IObjectFactory createFactory() {
        return new TetikusObjectFactory();
    }
}
