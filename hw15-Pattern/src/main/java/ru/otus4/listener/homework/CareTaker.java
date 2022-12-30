package ru.otus4.listener.homework;

import java.util.HashMap;
import java.util.Map;

public class CareTaker {
    private final Map<Long, Memento> map = new HashMap<>();

    public void add(Memento memento) {
        map.put(memento.getMessage().getId(), memento);
    }

    public Memento get(long id) {
        return map.get(id);
    }
}
