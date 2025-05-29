# MQTT Temperature Publisher & Subscriber (Spring Boot)

This Spring Boot system consists of two components communicating via MQTT:

- **Temperature Publisher** – generates and publishes random temperatures (15°C–30°C) every 5 seconds.
- **Temperature Subscriber** – subscribes to `sensor/temperature` topic and logs incoming temperature messages.

## Features

- MQTT integration using Eclipse Paho
- Publishes random temperature every 5 seconds
- Subscriber logs all received temperatures
- Graceful reconnection on connection loss
- Mosquitto as MQTT broker with persistence support

## Requirements

- Java 17+
- Docker & Docker Compose
- Mosquitto MQTT Broker
