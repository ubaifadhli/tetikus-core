package runner;

import di.TetikusObjectFactory;
import listener.TetikusListeners;
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
