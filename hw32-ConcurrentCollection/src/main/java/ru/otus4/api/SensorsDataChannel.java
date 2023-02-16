package ru.otus4.api;

import ru.otus4.api.model.SensorData;

import java.util.concurrent.TimeUnit;

public interface SensorsDataChannel {
    boolean push(SensorData sensorData);

    boolean isEmpty();

    SensorData take(long timeout, TimeUnit unit) throws InterruptedException;
}
