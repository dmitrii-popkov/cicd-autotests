package cicd.autotests.service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Map;

import static cicd.autotests.helper.AllureHelper.getAllureRestAssured;
import static cicd.autotests.service.auth.Session.SESSION_NAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultRequestSpecification {

	private static final String TIMEZONE_HEADER = "X-Timezone";
	private static final String LANGUAGE_HEADER = "Accept-Language";
	private static final String LANGUAGE = "ru";

	/**
	 * Спецификация запроса по умолчанию.
	 *
	 * @param url       URL для выполнения запроса
	 * @param sessionId идентификатор сеанса
	 * @return спецификацию запроса
	 */
	public static RequestSpecification getRequestSpecification(String url, String sessionId) {
		return getRequestSpecification(url, sessionId, ContentType.JSON, LogDetail.ALL);
	}

	/**
	 * Спецификация запроса по умолчанию.
	 *
	 * @param url       URL для выполнения запроса
	 * @param sessionId идентификатор сеанса
	 * @param lang      язык запроса
	 * @param headers   хэдеры
	 * @return спецификацию запроса
	 */
	public static RequestSpecification getRequestSpecification(String url, String sessionId, String lang,
															   Map<String, String> headers) {
		return getRequestSpecification(url, sessionId, lang, ContentType.JSON, LogDetail.ALL, headers);
	}

	/**
	 * Спецификация запроса с выбором уровня журналирования.
	 *
	 * @param url       URL для выполнения запроса
	 * @param sessionId идентификатор сеанса
	 * @param logDetail уровень подробностей при журналировании
	 * @return спецификацию запроса
	 */
	public static RequestSpecification getRequestSpecification(String url, String sessionId, LogDetail logDetail) {
		return getRequestSpecification(url, sessionId, ContentType.JSON, logDetail);
	}

	/**
	 * Спецификация запроса с выбором типа содержимого.
	 *
	 * @param url         URL для выполнения запроса
	 * @param sessionId   идентификатор сеанса
	 * @param contentType тип содержимого
	 * @return спецификацию запроса
	 */
	public static RequestSpecification getRequestSpecification(String url, String sessionId, ContentType contentType) {
		return getRequestSpecification(url, sessionId, contentType, LogDetail.ALL);
	}

	/**
	 * Спецификация запроса с выбором языка.
	 *
	 * @param url       URL для выполнения запроса
	 * @param sessionId идентификатор сеанса
	 * @param lang      язык запроса
	 * @return спецификацию запроса
	 */
	public static RequestSpecification getRequestSpecification(String url, String sessionId, String lang) {
		return getRequestSpecification(url, sessionId, lang, ContentType.JSON, LogDetail.ALL);
	}

	/**
	 * Спецификация запроса с выбором тип содержимого и уровня журналирования.
	 *
	 * @param url         URL для выполнения запроса
	 * @param sessionId   идентификатор сеанса
	 * @param contentType тип содержимого
	 * @param logDetail   уровень подробностей при журналировании
	 * @return спецификацию запроса
	 */
	public static RequestSpecification getRequestSpecification(String url,
															   String sessionId,
															   ContentType contentType,
															   LogDetail logDetail) {
		return getRequestSpecification(url, sessionId, LANGUAGE, contentType, logDetail);
	}

	/**
	 * Спецификация запроса со всеми основными настройками.
	 *
	 * @param url         URL для выполнения запроса
	 * @param sessionId   идентификатор сеанса
	 * @param lang        язык
	 * @param contentType тип содержимого
	 * @param logDetail   уровень подробности журналирования
	 * @return спецификацию запроса
	 */
	public static RequestSpecification getRequestSpecification(String url,
															   String sessionId,
															   String lang,
															   ContentType contentType,
															   LogDetail logDetail) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
			.setBaseUri(url)
			.setRelaxedHTTPSValidation()
			.addQueryParam("lang", lang)
			.addHeaders(Map.of(TIMEZONE_HEADER, ZonedDateTime.now().getZone().getId(), LANGUAGE_HEADER, lang))
			.setContentType(contentType)
			.log(logDetail)
			.build()
			.filter(getAllureRestAssured());

		if (sessionId != null) {
			requestSpecification.cookie(SESSION_NAME, sessionId);
		}

		return requestSpecification;
	}

	/**
	 * Спецификация запроса со всеми основными настройками.
	 *
	 * @param url         URL для выполнения запроса
	 * @param sessionId   идентификатор сеанса
	 * @param lang        язык
	 * @param contentType тип содержимого
	 * @param logDetail   уровень подробности журналирования
	 * @param headers     хэдеры
	 * @return спецификацию запроса
	 */
	public static RequestSpecification getRequestSpecification(String url,
															   String sessionId,
															   String lang,
															   ContentType contentType,
															   LogDetail logDetail,
															   Map<String, String> headers) {
		headers.put(TIMEZONE_HEADER, ZonedDateTime.now().getZone().getId());
		headers.put(LANGUAGE_HEADER, lang);

		RequestSpecification requestSpecification = new RequestSpecBuilder()
			.setBaseUri(url)
			.setRelaxedHTTPSValidation()
			.addQueryParam("lang", lang)
			.setContentType(contentType)
			.addHeaders(headers)
			.log(logDetail)
			.build()
			.filter(getAllureRestAssured());

		if (sessionId != null) {
			requestSpecification.cookie(SESSION_NAME, sessionId);
		}

		return requestSpecification;
	}

}