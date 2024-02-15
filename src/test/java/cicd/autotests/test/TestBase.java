package cicd.autotests.test;

import cicd.autotests.service.listener.TestListener;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import static cicd.autotests.config.AllureEnvironment.prepareAllureEnvironment;
import static cicd.autotests.helper.TestHelper.clearTestFiles;

/**
 * Подготовка к тестам
 */
@Slf4j
@Listeners({TestListener.class})
public class TestBase {

	@BeforeSuite(alwaysRun = true, groups = "suite", description = "Подготовка данных о тестовом окружении для Allure отчета")
	public void testSuiteSetUp() {
		log.info("Preparing before tests suite");

		prepareAllureEnvironment();
	}

	@BeforeClass(alwaysRun = true, description = "Подготовка перед запуском тестового класса")
	public void testClassSetUp() {
		clearTestFiles();
	}

	@AfterClass(alwaysRun = true, description = "Подготовка после завершения работы тестового класса")
	public void testClassTearDown() {
		clearTestFiles();
	}
}
