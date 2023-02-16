package ru.otus4.api;

import ru.otus4.api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
