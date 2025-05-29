package com.mqtt.temperature_subscriber.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TemperatureSubscriberService implements MqttCallback {
    private static final String TOPIC = "sensor/temperature";
    private final MqttClient mqttClient;
    private static final int QQS = 2;

    @PostConstruct
    public void subscribe() {
        try {
            mqttClient.setCallback(this);
            if (mqttClient.isConnected()) {
                mqttClient.subscribe(TOPIC, QQS);
                log.info("Connected and ready to receive messages on topic: {}", TOPIC);
            }
        } catch (MqttException e) {
            log.error("Failed to subscribe to topic {}: {}", TOPIC, e.getMessage(), e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.warn("Connection to MQTT broker lost: {}", cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        log.info("Received message - Topic: {}, Payload: {} °C",
                topic, payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Optional: Handle delivery completion if needed
    }
}
