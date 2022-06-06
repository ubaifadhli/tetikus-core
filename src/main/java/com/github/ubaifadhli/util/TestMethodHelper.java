package com.github.ubaifadhli.util;

import com.github.ubaifadhli.constants.PlatformConstant;
import org.testng.IInvokedMethod;

public class TestMethodHelper {
    private static String METHOD_ID_SEPARATOR = "-";

    public static PlatformConstant getPlatformByMethodID(String methodID) {
        String[] splitId = methodID.split(METHOD_ID_SEPARATOR);
        return PlatformConstant.valueOf(splitId[splitId.length - 1]);
    }

    public static String getMethodIDWithPlatform(IInvokedMethod invokedMethod) {
        return getMethodIDWithPlatform(invokedMethod.getTestMethod().getId());
    }

    public static String getMethodIDWithPlatform(String methodID) {
        return methodID + METHOD_ID_SEPARATOR + CurrentThreadDriver.getCurrentPlatform().name();
    }
}
