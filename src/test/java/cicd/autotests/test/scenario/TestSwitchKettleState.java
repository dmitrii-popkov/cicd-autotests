package cicd.autotests.test.scenario;

import cicd.autotests.dto.KettleInfo;
import cicd.autotests.dto.SwitchMode;
import cicd.autotests.helper.KettleHelper;
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
public class TestSwitchKettleState extends TestBase {

	private String kettleId = "";


	@BeforeClass
	public void setUp() throws InterruptedException {
		log.info("\n\nTestCase 1: preparing\n");

		KettleInfo kettleInfo = KettleHelper.getAvailableKettles(STANDALONE_URL).get(0);

		if (kettleInfo.getTemperature() > 90) {
			Thread.sleep(60000);
		}
	}

	@Test(description = "Test Case 1: Проверка состояния чайника после включения")
	@Description("Test Case 1: Проверка состояния чайника после включения")
	public void kettleStateSwitchTest() throws InterruptedException {
		log.info("\n\nTestCase 1: starting tests\n");

		getAvailableKettleIdStep();
		turnOnKettleStep();
		checkKettleStateStep(3, true);
		Thread.sleep(5000);
		turnOffKettleStep();
		checkKettleStateStep(5, false);

		log.info("\nTestCase 1: success, tests passed\n");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("\n\nTestCase 1: tear down\n");

	}

	@Step("Step 1: Получение id чайника")
	private void getAvailableKettleIdStep() {
		log.info("Step 1: Getting available kettle id");

		kettleId = KettleHelper.getAvailableKettles(STANDALONE_URL).get(0).getId();

		log.info("Getting available kettle id completed successfully");
		Allure.step("Получение id чайника прошло успешно");
	}

	@Step("Step 2: Подача команды на включение чайника")
	private void turnOnKettleStep() {
		log.info("Step 2: Sending turn on command to kettle");

		KettleHelper.turnOnKettle(STANDALONE_URL, kettleId);

		log.info("Sending turn on command to kettle completed successfully");
		Allure.step("Подача команды на включение чайника прошла успешно");
	}

	@Step("Step {step}: Проверка состояния чайника")
	private void checkKettleStateStep(int step, boolean isMustBoiling) throws InterruptedException {
		log.info("Step {}: Checking kettle state", step);

		Thread.sleep(5000);
		KettleInfo kettleInfo = KettleHelper.getAvailableKettles(STANDALONE_URL).get(0);

		boolean isCorrectState = kettleInfo.getSwitchMode().equals(SwitchMode.BOIL) == isMustBoiling;
		String condition = isMustBoiling ? "должен" : "не должен";

		assertThat(isCorrectState)
			.withFailMessage(String.format("Чайник с id {%s} {%s} кипятить", kettleId, condition))
			.isTrue();

		log.info("Checking turned on kettle state completed successfully");
		Allure.step("Проверка состояния чайника после включения прошла успешно");
	}

	@Step("Step 4: Подача команды на выключение чайника")
	private void turnOffKettleStep() throws InterruptedException {
		log.info("Step 4: Sending turn off command to kettle");

		KettleHelper.turnOffKettle(STANDALONE_URL, kettleId);

		log.info("Sending turn off command to kettle completed successfully");
		Allure.step("Подача команды на выключение чайника прошла успешно");
	}
}
