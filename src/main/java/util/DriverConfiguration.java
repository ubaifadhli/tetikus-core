package util;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

@Data
public class DriverConfiguration {
    private String platform;
    private String browser;
    private String url;
    private String location;
    private Map<String, String> desiredCapabilities = new HashMap<>();

    @JsonAnyGetter
    public Map<String, String> getDesiredCapabilitiesMap() { // TODO Change into private because dev only needs it in DesiredCapabilities class.
        return desiredCapabilities;
    }

    public DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        getDesiredCapabilitiesMap().forEach(desiredCapabilities::setCapability);

        return desiredCapabilities;
    }
}