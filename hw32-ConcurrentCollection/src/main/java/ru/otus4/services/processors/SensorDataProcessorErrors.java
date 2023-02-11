package ru.otus4.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus4.api.SensorDataProcessor;
import ru.otus4.api.model.SensorData;

public class SensorDataProcessorErrors implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorErrors.class);

    @Override
    public void process(SensorData data) {
        if (data.getValue() == null || !data.getValue().isNaN()) {
            return;
        }
        log.error("Обработка ошибочных данных: {}", data);
    }
}
