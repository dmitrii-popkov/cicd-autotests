package cicd.autotests.service.auth;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

@Slf4j
public class Session {

	public static final String SESSION_NAME = "JSESSIONID";
	private static final Map<String, String> sessionIdMap = new ConcurrentHashMap<>();

	public static void setSessionId(String url, String sessionId) {
		log.info("Setting session id [{}={}]", url, sessionId);
		Allure.step(format("Сохранение идентификатора сессии [%s=%s]", url, sessionId));

		sessionIdMap.put(url, sessionId);
	}

	public static void deleteSessionId(String url) {
		log.info("Deleting URL [{}] session id", url);
		Allure.step(format("Удаление идентификатора сессии [%s]", url));

		sessionIdMap.remove(url);
	}

	public static String getSessionId(String url) {
		return sessionIdMap.get(url);
	}

}
