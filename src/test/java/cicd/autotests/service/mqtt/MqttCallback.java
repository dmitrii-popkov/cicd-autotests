package cicd.autotests.service.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.function.BiConsumer;

public interface MqttCallback extends BiConsumer<String, MqttMessage> {

	void call(String topic, MqttMessage message) throws RuntimeException;

	@Override
	default void accept(String topic, MqttMessage message) {
		call(topic, message);
	}
}
