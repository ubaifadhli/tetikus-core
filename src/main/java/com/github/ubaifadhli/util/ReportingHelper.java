package com.github.ubaifadhli.util;

import com.github.ubaifadhli.constants.PlatformConstant;
import com.github.ubaifadhli.pages.PageObject;
import com.github.ubaifadhli.reports.ReportDetail;
import com.github.ubaifadhli.reports.TemplateEngine;
import com.github.ubaifadhli.reports.TestResult;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import lombok.SneakyThrows;
import org.testng.IInvokedMethod;
import org.testng.ISuite;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

public class ReportingHelper {
    public static String DEFAULT_REPORTING_DIRECTORY = "target/tetikus-report/";
    private static String BASE_RUNNER_NAME = "TetikusCoreRunner";
    private static String PLACEHOLDER_LOGO_FILENAME = "placeholder.svg";

    public static ReportDetail convertToTestResults(List<ISuite> suites) {
        long startMiilis = -1;
        long endMillis = -1;
        List<TestResult> testResults = new ArrayList<>();

        List<IInvokedMethod> invokedMethodList = suites.stream()
                .map(ISuite::getAllInvokedMethods)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (IInvokedMethod invokedMethod : invokedMethodList) {
            String methodName = invokedMethod.getTestMethod().getMethodName();
            String methodID = invokedMethod.getTestMethod().getId();
            boolean isSuccess = invokedMethod.getTestResult().isSuccess();
            Throwable thrownError = invokedMethod.getTestResult().getThrowable();

            PlatformConstant platform = TestMethodHelper.getPlatformByMethodID(methodID);

            long currentStartMillis = invokedMethod.getTestResult().getStartMillis();
            long currentEndMillis = invokedMethod.getTestResult().getEndMillis();

            startMiilis = startMiilis < 0 ? currentStartMillis : Math.min(startMiilis, currentStartMillis);
            endMillis = endMillis < 0 ? currentEndMillis : Math.max(endMillis, currentEndMillis);



            TestResult testResult = TestResult.builder()
                    .testName(methodName)
                    .isSuccess(isSuccess)
                    .platform(platform)
                    .errorThrown(thrownError != null ? getStackTraceAsString(thrownError) : null)
                    .calledMethods(ReportingHelper.getCalledMethodsByMethodName(methodName))
                    .screenshotFileName(ScreenshotHelper.getScreenshotName(methodID))
                    .build();

            testResults.add(testResult);
        }

        return ReportDetail.builder()
                .testResults(testResults)
                .startMillis(startMiilis)
                .endMillis(endMillis)
                .build();
    }

    private static String getStackTraceAsString(Throwable errorThrown) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        errorThrown.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    @SneakyThrows
    public static void generateReport(ReportDetail reportDetail) {
        Map<String, Object> dataMap = new HashMap<>();

        String startDate = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(reportDetail.getStartMillis()), ZoneOffset.of("+07:00")
        ).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

        Duration testDuration = Duration.between(
                Instant.ofEpochMilli(reportDetail.getStartMillis()),
                Instant.ofEpochMilli(reportDetail.getEndMillis())
        );

        long testDurationMinutes = testDuration.getSeconds() / 60;
        long testDurationRemainingSeconds = testDuration.getSeconds() % 60;

        StringBuilder testDurationInMinutesAndSeconds = new StringBuilder();

        if (testDurationMinutes > 0)
            testDurationInMinutesAndSeconds.append(testDurationMinutes)
                    .append(" minutes ");

        if (testDurationRemainingSeconds > 0)
            testDurationInMinutesAndSeconds.append(testDurationRemainingSeconds)
                    .append(" seconds");

        dataMap.put("testStartDate", startDate);
        dataMap.put("testDuration", testDurationInMinutesAndSeconds);
        dataMap.put("testResults", reportDetail.getTestResults());

        TemplateEngine.generateWebpage(dataMap);

        Files.copy(
                ReportingHelper.class.getClassLoader().getResourceAsStream("reports/" + PLACEHOLDER_LOGO_FILENAME),
                new File(ReportingHelper.DEFAULT_REPORTING_DIRECTORY + PLACEHOLDER_LOGO_FILENAME).toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );
    }

    public static List<String> getCalledMethodsByMethodName(String methodName) {
        ClassPool classPool = new ClassPool(true);
        classPool.appendSystemPath();

        List<MethodCall> calledMethods = new ArrayList<>();

        try {
            CtClass testClass = classPool.getCtClass(BASE_RUNNER_NAME);

            CtMethod currentMethod = testClass.getDeclaredMethod(methodName);

            currentMethod.instrument(new ExprEditor() {
                public void edit(MethodCall methodCall) {
                    calledMethods.add(methodCall);
                }
            });

        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch names");
        }

        return calledMethods.stream()
                .filter(ReportingHelper::doesMethodClassExtendPageObject)
                .map(MethodCall::getMethodName)
                .collect(Collectors.toList());
    }

    public static boolean doesMethodClassExtendPageObject(MethodCall methodCall) {
        boolean doesExtendPageObject;
        boolean hasSuperclass;

        try {
            CtClass methodClass = methodCall.getMethod().getDeclaringClass();

            String pageObjectQualifiedName = PageObject.class.getName();

            do {
                doesExtendPageObject = methodClass.getName().equals(pageObjectQualifiedName);

                methodClass = methodClass.getSuperclass();
                hasSuperclass = methodClass.getName() != null &&
                        !methodClass.getName().equals(Object.class.getName());

            } while (!doesExtendPageObject && hasSuperclass);

        } catch (NotFoundException | SecurityException e) {
            doesExtendPageObject = false;
        }

        return doesExtendPageObject;
    }
}
