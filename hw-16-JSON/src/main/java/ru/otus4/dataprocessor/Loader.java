package ru.otus4.dataprocessor;

import ru.otus4.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
