package cicd.autotests.service;

import cicd.autotests.service.auth.Session;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

import static cicd.autotests.helper.AllureHelper.getAllureRestAssured;
import static cicd.autotests.service.DefaultRequestSpecification.getRequestSpecification;
import static cicd.autotests.service.DefaultResponceSpecification.getResponseSpecification;
import static io.restassured.RestAssured.given;

/**
 * Шаблоны методов для получения ответов по API-запросам.
 */
public class RequestTemplate {

	/**
	 * Шаблон для получения ответа по GET-запросу.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromGetRequest(RequestSpecification requestSpecification,
												  String endpoint,
												  Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.when()
			.get(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по GET-запросу с возможностью указать уровень журналирования и ожидаемый код ответа.
	 *
	 * @param url                  основной URL запроса
	 * @param endpoint             относительный URL запроса
	 * @param requestLogDetail     уровень журналирования для запроса
	 * @param responseLogDetail    уровень журналирования для ответа
	 * @param expectedStatusCode   ожидаемый код ответа
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromGetRequest(String url,
												  String endpoint,
												  LogDetail requestLogDetail,
												  LogDetail responseLogDetail,
												  int expectedStatusCode,
												  Object... additionalParameters) {
		return given()
			.spec(getRequestSpecification(url, Session.getSessionId(url), requestLogDetail))
			.when()
			.get(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification(expectedStatusCode, responseLogDetail))
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по POST-запросу.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param endpoint             относительный URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPostRequest(RequestSpecification requestSpecification, String endpoint) {
		return given()
			.spec(requestSpecification)
			.when()
			.post(endpoint)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по POST-запросу с возможностью указать уровень журналирования и ожидаемый код ответа.
	 *
	 * @param url                основной URL запроса
	 * @param endpoint           относительный URL запроса
	 * @param requestLogDetail   уровень журналирования для запроса
	 * @param responseLogDetail  уровень журналирования для ответа
	 * @param expectedStatusCode ожидаемый код ответа
	 * @return ответ от сервера
	 */
	public static Response responseFromPostRequest(String url,
												   String sessionId,
												   String endpoint,
												   LogDetail requestLogDetail,
												   LogDetail responseLogDetail,
												   int expectedStatusCode) {
		return given()
			.spec(getRequestSpecification(url, sessionId, requestLogDetail))
			.when()
			.post(endpoint)
			.then()
			.spec(getResponseSpecification(expectedStatusCode, responseLogDetail))
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по POST-запросу с пустым телом.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPostRequest(RequestSpecification requestSpecification,
												   String endpoint,
												   Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.when()
			.post(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по POST-запросу с пустым телом.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param multiPartKey         ключ передаваемого свойства
	 * @param multiPartValue       значение передаваемого свойства
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPostRequest(RequestSpecification requestSpecification,
												   String multiPartKey,
												   Object multiPartValue,
												   String endpoint,
												   Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.multiPart(multiPartKey, multiPartValue)
			.when()
			.post(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по POST-запросу с непустым телом.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param bodyObject           тело запроса
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPostRequest(RequestSpecification requestSpecification,
												   Object bodyObject,
												   String endpoint,
												   Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.when()
			.body(bodyObject)
			.post(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	public static Response responseFromPostRequestWithoutBody(String url, String endpoint, Map<String, ?> pathParameters) {
		return given()
			.spec(new RequestSpecBuilder()
				.log(LogDetail.ALL)
				.setBaseUri(url)
				.setRelaxedHTTPSValidation()
				.addPathParams(pathParameters)
				.noContentType()
				.build()).filter(getAllureRestAssured())
			.when()
			.post(endpoint)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по POST-запросу с указанием типа содержимого и загрузкой файла.
	 *
	 * @param fileToUpload         файл для загрузки
	 * @param requestSpecification спецификация запроса
	 * @param contentType          тип содержимого
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPostRequest(File fileToUpload,
												   RequestSpecification requestSpecification,
												   ContentType contentType,
												   String endpoint,
												   Object... additionalParameters) {
		return given()
			.multiPart(fileToUpload)
			.spec(requestSpecification)
			.contentType(contentType)
			.when()
			.post(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по POST-запросу с указанием типа содержимого и загрузкой файла.
	 *
	 * @param fileToUploadName     имя файла для Form Data
	 * @param fileToUpload         файл для загрузки
	 * @param requestSpecification спецификация запроса
	 * @param contentType          тип содержимого
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPostRequest(String fileToUploadName,
												   File fileToUpload,
												   RequestSpecification requestSpecification,
												   ContentType contentType,
												   String endpoint,
												   Object... additionalParameters) {
		return given()
			.multiPart(fileToUploadName, fileToUpload)
			.spec(requestSpecification)
			.contentType(contentType)
			.when()
			.post(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по PUT-запросу.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPutRequest(RequestSpecification requestSpecification,
												  String endpoint,
												  Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.when()
			.put(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по PUT-запросу с непустым телом.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param bodyObject           тело запроса
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPutRequest(RequestSpecification requestSpecification,
												  Object bodyObject,
												  String endpoint,
												  Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.when()
			.body(bodyObject)
			.put(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по PATCH-запросу с непустым телом.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param bodyObject           тело запроса
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPatchRequest(RequestSpecification requestSpecification,
													Object bodyObject,
													String endpoint,
													Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.when()
			.body(bodyObject)
			.patch(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по PATCH-запросу с пустым телом.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromPatchRequestWithoutBody(RequestSpecification requestSpecification,
															   String endpoint,
															   Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.when()
			.patch(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по DELETE-запросу.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromDeleteRequest(RequestSpecification requestSpecification,
													 String endpoint,
													 Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.when()
			.delete(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по DELETE-запросу.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param endpoint             относительный URL запроса
	 * @param body                 дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromDeleteRequestWithBody(RequestSpecification requestSpecification,
															 String endpoint,
															 Object body) {
		return given()
			.spec(requestSpecification)
			.when()
			.body(body)
			.delete(endpoint)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}

	/**
	 * Шаблон для получения ответа по DELETE-запросу.
	 *
	 * @param requestSpecification спецификация запроса
	 * @param bodyObject           тело запроса
	 * @param endpoint             относительный URL запроса
	 * @param additionalParameters дополнительные параметры для заполнителей в URL запроса
	 * @return ответ от сервера.
	 */
	public static Response responseFromDeleteRequestWithBody(RequestSpecification requestSpecification,
															 Object bodyObject,
															 String endpoint,
															 Object... additionalParameters) {
		return given()
			.spec(requestSpecification)
			.when()
			.body(bodyObject)
			.delete(endpoint, additionalParameters)
			.then()
			.spec(getResponseSpecification())
			.extract().response();
	}
}
