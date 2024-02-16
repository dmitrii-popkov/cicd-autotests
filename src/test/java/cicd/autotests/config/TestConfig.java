package cicd.autotests.config;

import lombok.Getter;

/**
 * Чтение из application.yml.
 */
@Getter
public class TestConfig {

	private EndpointConfig endpoint;

	private ResourcesDirPath resourcesDirPath;

	private AuthConfig auth;

	@Getter
	public static class EndpointConfig {
		private String turnOn;
		private String turnOff;
		private String availableKettles;
		private String heat;
	}

	@Getter
	public static class ResourcesDirPath {
		private String testFilesDirPath;
	}

	@Getter
	public static class AuthConfig {
		private String standaloneUrl;
		private String mqttUrl;
	}
}
