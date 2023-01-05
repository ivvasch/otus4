package ru.otus4.dataprocessor;

import org.junit.jupiter.api.Test;
import ru.otus4.model.Measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProcessorAggregatorTest {

    @Test
    public void mapAgregator() {
        Measurement measurement = new Measurement("name", 10.0);
        Measurement measurement2 = new Measurement("name", 12.0);
        Measurement measurement3 = new Measurement("name2", 9.1);
        Measurement measurement4 = new Measurement("name2", 8.4);

        List<Measurement> list = new ArrayList<>();

        list.add(measurement);
        list.add(measurement2);
        list.add(measurement3);
        list.add(measurement4);


        ProcessorAggregator processorAggregator = new ProcessorAggregator();
        Map<String, Double> groupMap = processorAggregator.process(list);

        assertThat(groupMap.size()).isEqualTo(2);

    }

}
