package com.github.ubaifadhli.reports;

import com.github.ubaifadhli.util.ReportingHelper;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import lombok.SneakyThrows;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class TemplateEngine {
    private static String REPORT_WEBPAGE_PATH = ReportingHelper.DEFAULT_REPORTING_DIRECTORY + "index.html";
    private static String TEMPLATE_FILENAME_PATH = "reports/template.html";
    private static PebbleEngine PEBBLE_ENGINE = new PebbleEngine.Builder().build();

    @SneakyThrows
    public static void generateWebpage(Map<String, Object> dataMap) {
        PebbleTemplate compiledTemplate = PEBBLE_ENGINE.getTemplate(TEMPLATE_FILENAME_PATH);

        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, dataMap);

        String output = writer.toString();

        Path path = Paths.get(REPORT_WEBPAGE_PATH);

        Files.write(path, output.getBytes(StandardCharsets.UTF_8));

        // TODO Add log linking to the file path for easier webpage opening
    }
}
