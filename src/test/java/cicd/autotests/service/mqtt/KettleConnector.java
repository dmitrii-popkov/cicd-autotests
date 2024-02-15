package cicd.autotests.service.mqtt;

import java.util.Collection;

public interface KettleConnector extends AutoCloseable {

	void startListening() throws RuntimeException;

	void stopListening() throws RuntimeException;

	Collection<String> getAvailableIds();

	void turnOn(String id) throws RuntimeException;

	void turnOff(String id) throws RuntimeException;

	static KettleConnector create(String url) {
		return null; // TODO: 13.02.2024 tbd
	}
}
