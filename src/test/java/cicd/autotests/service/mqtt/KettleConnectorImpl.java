package cicd.autotests.service.mqtt;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class KettleConnectorImpl implements KettleConnector {

	private final ConcurrentHashMap<String, KettleState> knownKettles = new ConcurrentHashMap<>();
	private final AtomicReference<MqttConnector> mqttStorage = new AtomicReference<>();

	private final String connectionUrl;

	public KettleConnectorImpl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	@Override
	public void startListening() throws RuntimeException {
		try {
			MqttConnector mqttConnector = MqttConnector.create(connectionUrl);
			try (MqttConnector previousConnector = mqttStorage.getAndSet(mqttConnector)) {
				// TODO: 14.02.2024 log closing
			} catch (RuntimeException e) {
				// TODO: 14.02.2024 log failure to close and handle dangling
			}
			mqttConnector.subscribe("polaris/+/+/state/last_wifi", ((topic, message) -> {
				String[] topicParts = topic.split("/");
				String kettleId = topicParts[1] + topicParts[2];
				// TODO: 14.02.2024 add sub to state
				knownKettles.putIfAbsent(kettleId, KettleState.ACTIVE);
			}));
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void stopListening() throws RuntimeException {
		try {
			try (MqttConnector previousConnector = mqttStorage.getAndSet(null)) {
				// TODO: 14.02.2024 log closing
			} catch (RuntimeException e) {
				// TODO: 14.02.2024 log failure to close and handle dangling
			}
			knownKettles.clear();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<String> getAvailableIds() {
		return knownKettles.keySet(KettleState.ACTIVE).stream().toList();
	}

	@Override
	public void turnOn(String id) throws RuntimeException {
		MqttConnector mqttConnector = mqttStorage.get();
		if (mqttConnector == null) {
			throw new RuntimeException("Broker connection not established");
		}
		try {
			mqttConnector.publish("polaris/%s/%s/control/mode".formatted(id.substring(0, 2), id.substring(2)), "1");
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void turnOff(String id) throws RuntimeException {
		MqttConnector mqttConnector = mqttStorage.get();
		if (mqttConnector == null) {
			throw new RuntimeException("Broker connection not established");
		}
		try {
			mqttConnector.publish("polaris/%s/%s/control/mode".formatted(id.substring(0, 2), id.substring(2)), "0");
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() throws RuntimeException {
		try (MqttConnector mqttConnector = mqttStorage.get()) {
			// TODO: 13.02.2024 logger info
		}
	}
}
