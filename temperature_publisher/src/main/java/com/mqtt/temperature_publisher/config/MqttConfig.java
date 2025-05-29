package com.mqtt.temperature_publisher.config;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MqttConfig {
    private static final String BROKER = "tcp://localhost:1883";
    private static final String CLIENT_ID = "spring-publisher";
    private MqttClient mqttClient;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        mqttClient = new MqttClient(BROKER, CLIENT_ID, new MqttDefaultFilePersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        mqttClient.connect(options);
        log.info("Connected to MQTT broker: {}", BROKER);
        return mqttClient;
    }

    @PreDestroy
    public void onDestroy() {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.disconnect();
                mqttClient.close();
                log.info("Disconnected MQTT client: {}", mqttClient.getClientId());
            }
        } catch (MqttException e) {
            log.error("Failed to disconnect MQTT client: {}", e.getMessage(), e);
        }
    }
}
