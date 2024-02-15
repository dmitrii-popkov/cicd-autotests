package cicd.autotests.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookupFactory;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


/**
 * Реализация логики загрузки yml файла
 */

public class ConfigurationLoader {

    private final ObjectMapper objectMapper;
    private final StringSubstitutor stringSubstitutor;

    ConfigurationLoader() {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
        this.stringSubstitutor = new StringSubstitutor(StringLookupFactory.INSTANCE.environmentVariableStringLookup());
    }

    TestConfig loadConfiguration(File config) {
        try {
            String contents = this.stringSubstitutor.replace(Files.readString(config.toPath(), StandardCharsets.UTF_8));

            return this.objectMapper.readValue(contents, TestConfig.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}