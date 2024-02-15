package cicd.autotests.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * Считывание данных из конфигурационного yml-файла.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoadFile {

    @Getter
    private static final TestConfig testConfig = new ConfigurationLoader()
        .loadConfiguration(new File("src/test/resources/application.yml"));

}
