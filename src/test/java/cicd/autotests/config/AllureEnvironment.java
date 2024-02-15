package cicd.autotests.config;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static cicd.autotests.config.TestProperties.STANDALONE_URL;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

@Slf4j
public class AllureEnvironment {

	private static final String PATH = "target/allure-results/";
	private static final String FILENAME = "environment.xml";

	public static void prepareAllureEnvironment() {
		try {
			log.info("Start writing Allure env");

			if (new File(PATH, FILENAME).delete()) {
				log.info("File deleted successfully");
			} else {
				log.error("Failed to delete the file");
			}

			allureEnvironmentWriter(
				ImmutableMap.<String, String>builder()
					.put("Product name", "Kettle")
					.put("Tested machine", STANDALONE_URL)
					.build(),
				PATH);

			log.info("Allure environment written");
		} catch (AssertionError e) {
			log.error(e.getMessage());
		}
	}

}