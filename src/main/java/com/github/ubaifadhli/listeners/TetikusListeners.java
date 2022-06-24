package com.github.ubaifadhli.listeners;

import com.github.ubaifadhli.reports.ReportDetail;
import com.github.ubaifadhli.util.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;

@Slf4j
public class TetikusListeners implements ISuiteListener, IInvokedMethodListener, IReporter {
    @Override
    public void onStart(ISuite suite) {
        int driverConfigurationsCount = DriverConfigurationHelper.getDriverConfigurationsCount();

        // Configuring it to be able to run in parallel.
        suite.getXmlSuite().setParallel(XmlSuite.ParallelMode.TESTS);

        // Configuring how many threads that can be run in parallel.
        suite.getXmlSuite().setThreadCount(driverConfigurationsCount);

        // Configuring invocation count so that it can run both mobile and web test in parallel.
        for (ITestNGMethod method : suite.getAllMethods()) {
            method.setInvocationCount(driverConfigurationsCount);
            method.setThreadPoolSize(driverConfigurationsCount);
        }

        // Store driver configurations to List, so those could be initialized by IInvokedMethodListener.
        List<DriverConfiguration> driverConfigurations = DriverConfigurationHelper.getDriverConfigurations();
        CurrentThreadDriver.readConfigurations(driverConfigurations);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        CurrentThreadDriver.initializeDriverForCurrentThread();

        method.getTestMethod().setId(TestMethodHelper.getMethodIDWithPlatform(method));
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        ScreenshotHelper.getScreenshotForCurrentPlatform(getMethodID(method));

        CurrentThreadDriver.destroyDriverForCurrentThread();
    }


    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        if (PropertiesHelper.isReportEnabled()) {
            ReportDetail testResults = ReportingHelper.convertToTestResults(suites);
            ReportingHelper.generateReport(testResults);

        } else
            log.info("Report is currently configured to be disabled. Report would not be generated.");
    }

    private String getMethodID(IInvokedMethod invokedMethod) {
        return invokedMethod.getTestMethod().getId();
    }
}
