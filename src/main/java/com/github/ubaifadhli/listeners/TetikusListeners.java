package com.github.ubaifadhli.listeners;

import com.github.ubaifadhli.util.CurrentThreadDriver;
import com.github.ubaifadhli.util.DriverConfiguration;
import com.github.ubaifadhli.util.DriverConfigurationHelper;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;

@Slf4j
public class TetikusListeners implements ISuiteListener, IInvokedMethodListener {
    @Override
    public void onStart(ISuite suite) {
        int driverConfigurationsCount = DriverConfigurationHelper.getDriverConfigurationsCount();

        // Configuring it to be able to run in parallel.
        suite.getXmlSuite().setParallel(XmlSuite.ParallelMode.TESTS);

        // Configuring how many threads that can be run in parallel.
        suite.getXmlSuite().setThreadCount(driverConfigurationsCount);

        // Configuring invocation count so that it can run both mobile and web test in parallel.
        suite.getAllMethods().forEach(method -> {
            method.setInvocationCount(driverConfigurationsCount);
            method.setThreadPoolSize(driverConfigurationsCount);
        });

        // Store driver configurations to List, so those could be initialized by IInvokedMethodListener.
        List<DriverConfiguration> driverConfigurations = DriverConfigurationHelper.getDriverConfigurations();
        CurrentThreadDriver.readConfigurations(driverConfigurations);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        CurrentThreadDriver.initializeDriverForCurrentThread();
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        CurrentThreadDriver.destroyDriverForCurrentThread();
    }
}
