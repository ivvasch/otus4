package ru.otus4.dataprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus4.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String filename;

    public ResourcesFileLoader(String filename) {
        this.filename = filename;
    }


    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        List<Measurement> list = new ArrayList<>();
        InputStream resourceAsStream = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(filename);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(resourceAsStream);
            for (JsonNode next : node) {
                Measurement measurement = new Measurement(next.get("name").asText()
                        , Double.parseDouble(String.valueOf(next.get("value"))));
                list.add(measurement);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
