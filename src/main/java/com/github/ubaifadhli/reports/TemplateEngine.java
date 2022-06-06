package com.github.ubaifadhli.reports;

import com.github.ubaifadhli.util.ReportingHelper;
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
    private static String TEMPLATE_FILENAME_PATH = "src/main/resources/template.html";

    @SneakyThrows
    public static void generateWebpage(Map<String, Object> dataMap) {
////        Map<String, Object> context = new HashMap<>();
////        context.put("data", data);
//
//        PebbleEngine engine = new PebbleEngine.Builder().build();
//        PebbleTemplate compiledTemplate = engine.getTemplate(TEMPLATE_FILENAME_PATH);
//
//        Writer writer = new StringWriter();
//        compiledTemplate.evaluate(writer, dataMap);
////        compiledTemplate.evaluate(writer, context);
//
//        String output = writer.toString();
//
//        Path path = Paths.get(REPORT_WEBPAGE_PATH);
//
////        path.getParent().toFile().mkdirs();
//
//        Files.write(path, output.getBytes(StandardCharsets.UTF_8));
//
//        // TODO Add log linking to the file path for easier webpage opening
    }
}
