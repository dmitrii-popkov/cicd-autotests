package cicd.autotests.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static cicd.autotests.config.TestProperties.TEST_FILES_PATH;
import static io.qameta.allure.model.Status.FAILED;
import static java.lang.String.format;

@Slf4j
public class TestHelper {

	public static void clearTestFiles() {
		clearTestFiles(TEST_FILES_PATH);
	}

	@Step("Очистка директории с файлами теста [{dirPath}]")
	public static void clearTestFiles(String dirPath) {
		log.info("Clearing directory with test files [{}]", dirPath);

		try {
			FileUtils.cleanDirectory(new File(dirPath));
			FileUtils.touch(new File(dirPath, ".gitkeep"));

			log.info("Clearing directory with test files [{}] completed successfully", dirPath);
			Allure.step(format("Очистка директории с тестовыми файлами [%s] прошла успешно", dirPath));
		} catch (IOException e) {
			String message = e.getMessage();

			log.error("Exception [{}]", message);
			Allure.step(format("При очистке директории [%s] что-то пошло не так [%s]", dirPath, message), FAILED);
		}
	}
}