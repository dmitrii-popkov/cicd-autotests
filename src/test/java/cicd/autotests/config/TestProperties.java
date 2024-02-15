package cicd.autotests.config;

import java.util.Properties;


public abstract class TestProperties {

	public static final Properties SYSTEM_PROPERTIES = System.getProperties();
	private static final TestConfig TEST_CONFIG = LoadFile.getTestConfig();

	public static final String LOCALHOST = "127.0.0.1";
	public static final String STANDALONE_URL = SYSTEM_PROPERTIES.getProperty("standalone_url", TEST_CONFIG.getAuth().getStandaloneUrl());
	public static final String MQTT_URL = TEST_CONFIG.getAuth().getMqttUrl();

	public static final String TEST_FILES_PATH = TEST_CONFIG.getResourcesDirPath().getTestFilesDirPath();
	private static final TestConfig.EndpointConfig ENDPOINTS = TEST_CONFIG.getEndpoint();

	//Endpoints

	public static final String TURN_ON_KETTLE = ENDPOINTS.getTurnOn();
	public static final String TURN_OFF_KETTLE = ENDPOINTS.getTurnOff();
	public static final String GET_AVAILABLE_KETTLES = ENDPOINTS.getAvailableKettles();
}
