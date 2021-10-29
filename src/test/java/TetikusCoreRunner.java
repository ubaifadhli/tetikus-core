import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.DriverConfigurationParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CurrentThreadDriver;
import util.DriverConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


// TODO how am i supposed to extends this when dev gonna use it
public class TetikusCoreRunner {



    @Test()
    public void initializeTestDriver(DriverConfiguration driverConfiguration) {
        CurrentThreadDriver.initializeDriver(driverConfiguration);
    }
}

