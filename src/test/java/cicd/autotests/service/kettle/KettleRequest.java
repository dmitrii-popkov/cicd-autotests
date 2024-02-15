package cicd.autotests.service.kettle;

import cicd.autotests.service.RequestTemplate;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static cicd.autotests.config.TestProperties.GET_AVAILABLE_KETTLES;
import static cicd.autotests.config.TestProperties.TURN_OFF_KETTLE;
import static cicd.autotests.config.TestProperties.TURN_ON_KETTLE;
import static cicd.autotests.service.DefaultRequestSpecification.getRequestSpecification;
import static cicd.autotests.service.auth.Session.getSessionId;

@Slf4j
public class KettleRequest {

	@Step("Выполнение запроса на включение чайника [{url}] с id [{id}]")
	public static Response turnOnKettleRequest(String url, String id) {
		log.info("Creating request to turn on kettle [{}] with id [{}]", url, id);

		return RequestTemplate.responseFromPostRequestWithoutBody(url, TURN_ON_KETTLE,
			Map.of("id", id));
	}

	@Step("Выполнение запроса на выключение чайника [{url}] с id [{id}]")
	public static Response turnOffKettleRequest(String url, String id) {
		log.info("Creating request to turn off kettle [{}] with id [{}]", url, id);

		return RequestTemplate.responseFromPostRequestWithoutBody(url, TURN_OFF_KETTLE,
			Map.of("id", id));
	}

	@Step("Выполнение запроса на получение идентификаторов доступных чайников [{url}]")
	public static Response getAvailableKettlesIdsRequest(String url) {
		log.info("Creating request to get available kettles ids [{}]", url);

		return RequestTemplate.responseFromGetRequest(getRequestSpecification(url, getSessionId(url)), GET_AVAILABLE_KETTLES);
	}
}
