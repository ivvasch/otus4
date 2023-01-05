package ru.otus4.dataprocessor;

import ru.otus4.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> groupMap = new TreeMap<>();
        data.forEach(measurement -> {
            Double value = groupMap.get(measurement.getName()) == null
                    ? measurement.getValue()
                    : groupMap.get(measurement.getName()) + measurement.getValue();
            groupMap.put(measurement.getName(), value);
        });
        return groupMap;
    }
}
