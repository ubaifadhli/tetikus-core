package com.github.ubaifadhli.runners;

import com.github.ubaifadhli.di.TetikusObjectFactory;
import com.github.ubaifadhli.listeners.TetikusListeners;
import org.testng.IObjectFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.ObjectFactory;

/**
 * Base class to execute tests. Implements {@link TetikusListeners} to be able configure test execution.
 *
 * @author Fadhli Ubai
 */
@Listeners({TetikusListeners.class})
public class TetikusBaseRunner {
    @ObjectFactory
    public IObjectFactory createFactory() {
        return new TetikusObjectFactory();
    }
}
