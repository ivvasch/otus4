package ru.otus4.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus4.api.SensorDataProcessor;
import ru.otus4.api.model.SensorData;
import ru.otus4.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final ArrayBlockingQueue<SensorData> bufferedData;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        bufferedData = new ArrayBlockingQueue<>(bufferSize);
    }

    @Override
    public void process(SensorData data) {
        synchronized (bufferedData) {
            bufferedData.add(data);
            if (bufferedData.size() >= bufferSize) {
                flush();
            }
        }
    }

    public void flush() {
        synchronized (bufferedData) {
            if (!bufferedData.isEmpty()) {
                List<SensorData> dataList = new ArrayList<>(bufferedData);
                dataList.sort(Comparator.comparing(SensorData::getMeasurementTime));
                try {
                    writer.writeBufferedData(dataList);
                    bufferedData.clear();
                } catch (Exception e) {
                    log.error("Ошибка в процессе записи буфера", e);
                }
            }
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();

    }

}