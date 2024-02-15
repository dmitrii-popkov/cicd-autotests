package cicd.autotests.helper;

import cicd.autotests.dto.KettleInfo;
import cicd.autotests.service.kettle.KettleRequest;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class KettleHelper {

	@Step("Включение чайника [{url}] с id [{id}]")
	public static void turnOnKettle(String url, String id) {
		log.info("Turning on kettle [{}] with id [{}]", url, id);

		Response response = KettleRequest.turnOnKettleRequest(url, id);


		assertThat(response.statusCode())
			.withFailMessage("Код ответа при включении чайника должен быть 200")
			.isEqualTo(200);

		log.info("Turning on kettle completed successfully");
		Allure.step("Включение чайника прошло успешно");
	}

	@Step("Выключение чайника [{url}] с id [{id}]")
	public static void turnOffKettle(String url, String id) {
		log.info("Turning off kettle [{}] with id [{}]", url, id);

		Response response = KettleRequest.turnOffKettleRequest(url, id);


		assertThat(response.statusCode())
			.withFailMessage("Код ответа при выключении чайника должен быть 200")
			.isEqualTo(200);

		log.info("Turning off kettle completed successfully");
		Allure.step("Выключение чайника прошло успешно");
	}

	@Step("Получение списка инедтификаторов доступных чайников [{url}]")
	public static List<KettleInfo> getAvailableKettles(String url) {
		log.info("Get available kettles ids [{}]", url);

		Response response = KettleRequest.getAvailableKettlesIdsRequest(url);
		List<KettleInfo> availableKettlesInfo = response.jsonPath().getList("", KettleInfo.class);

		assertThat(response.statusCode())
			.withFailMessage("Код ответа при получении доступных чайников должен быть 200")
			.isEqualTo(200);

		log.info("Get available kettles ids completed successfully");
		Allure.step("Получение списка инедтификаторов доступных чайников прошло успешно");

		return availableKettlesInfo;
	}
}
