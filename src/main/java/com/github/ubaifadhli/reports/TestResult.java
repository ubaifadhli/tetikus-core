package com.github.ubaifadhli.reports;

import com.github.ubaifadhli.constants.PlatformConstant;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestResult {
    private String testName;
    private PlatformConstant platform;
    private boolean isSuccess;
    private String errorThrown;
    private List<String> calledMethods;
    private String screenshotFileName;
}
