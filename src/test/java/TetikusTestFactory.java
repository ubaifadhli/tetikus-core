import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.DriverConfigurationParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import util.DriverConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class TetikusTestFactory {
    private String DEFAULT_DRIVER_CONFIGURATION_PATH = "/driverconfig.json";

//    @Factory(dataProvider = "driverconfig")
//    public Object[] defaultFactoryMethod() {
//    }

    @DataProvider(name = "driverconfig", parallel = true)
    public Object[][] defaultDataProviderMethod() {
        List<DriverConfiguration> driverConfigurations = getDriverConfigurations();
        return convertToTwoDimensionalArray(driverConfigurations);
    }

    // TODO This could be in a separated helper class
    private <T> Object[][] convertToTwoDimensionalArray(List<T> objects) {
        return objects.stream()
                .map(object -> new Object[]{object})
                .toArray(size -> new Object[size][1]);
    }

    // TODO This could be in a separated helper class
    private List<DriverConfiguration> getDriverConfigurations() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream driverConfigurationsStream = TetikusCoreRunner.class.getResourceAsStream(DEFAULT_DRIVER_CONFIGURATION_PATH);

        try {
            return Arrays.asList(objectMapper.readValue(driverConfigurationsStream, DriverConfiguration[].class));
        } catch (IOException e) {
            throw new DriverConfigurationParseException(e);
        }
    }
}
