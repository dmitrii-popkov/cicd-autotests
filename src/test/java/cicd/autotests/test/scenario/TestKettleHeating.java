package cicd.autotests.test.scenario;

import cicd.autotests.dto.KettleInfo;
import cicd.autotests.dto.SwitchMode;
import cicd.autotests.helper.KettleHelper;
import cicd.autotests.helper.TestHelper;
import cicd.autotests.test.TestBase;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static cicd.autotests.config.TestProperties.STANDALONE_URL;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Feature("Чайник")
public class TestKettleHeating extends TestBase {

	public static final int KETTLE_HEATING_SECONDS = 100;
	private String kettleId;
	private int heatingTemperature;

	@BeforeClass
	public void setUp() {
		log.info("\n\nTestCase 2: preparing\n");

		KettleInfo kettleInfo = KettleHelper.getAvailableKettles(STANDALONE_URL).get(0);

		if (kettleInfo.getTemperature() > 70) {
			TestHelper.sleep(60000);
		}
	}

	@Test(description = "Test Case 2: Проверка работы режима нагрева до указанной температуры")
	@Description("Test Case 2: Проверка работы режима нагрева до указанной температуры")
	public void kettleHeatingTest() {
		log.info("\n\nTestCase 2: starting tests\n");

		getAvailableKettleIdStep();
		setKettleHeatingTemperatureStep();
		checkingHeatingStep();


		log.info("\nTestCase 2: success, tests passed\n");
	}

	@Step("Step 1: Получение id чайника")
	private void getAvailableKettleIdStep() {
		log.info("Step 1: Getting available kettle id");

		kettleId = KettleHelper.getAvailableKettles(STANDALONE_URL).get(0).getId();

		log.info("Getting available kettle id completed successfully");
		Allure.step("Получение id чайника прошло успешно");
	}

	@Step("Step 2: Установка температуры для нагрева чайника")
	private void setKettleHeatingTemperatureStep() {
		log.info("Step 2: Setting heating temperature");

		int currentTemperature = getCurrentKettleTemperature();
		heatingTemperature = currentTemperature + 5;

		KettleHelper.setHeatingTemperature(STANDALONE_URL, kettleId, heatingTemperature);

		log.info("Setting heating temperature completed successfully");
		Allure.step("Установка температуры для нагрева чайника прошла успешно");
	}

	@Step("Step 3: Проверка режима нагрева")
	private void checkingHeatingStep() {
		log.info("Step 3: Checking heating mode");

		int allowHeatedTemperature = heatingTemperature - 2;
		boolean isHeated = false;

		for (int i = 0; i < KETTLE_HEATING_SECONDS; i++) {
			TestHelper.sleep(1000);
			KettleInfo currentInfo = KettleHelper.getAvailableKettles(STANDALONE_URL).get(0);

			if (isKettleHeated(currentInfo, allowHeatedTemperature)) {
				isHeated = true;
				break;
			}
		}

		assertThat(isHeated)
			.withFailMessage("Спустя {} секунд чайник должен был нагреться", KETTLE_HEATING_SECONDS)
			.isTrue();

		log.info("Checking heating mode completed successfully");
		Allure.step("Проверка режима нагрева прошла успешно");
	}

	private boolean isKettleHeated(KettleInfo kettleInfo, int heatingTemperature) {
		return kettleInfo.getSwitchMode().equals(SwitchMode.OFF) && kettleInfo.getTemperature() >= heatingTemperature;
	}

	private int getCurrentKettleTemperature() {
		return KettleHelper.getAvailableKettles(STANDALONE_URL).get(0).getTemperature();
	}


	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("\n\nTestCase 2: tear down\n");

	}
}
