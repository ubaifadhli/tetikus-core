package com.github.ubaifadhli.reports;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReportDetail {
    List<TestResult> testResults;
    private long startMillis;
    private long endMillis;
}
