package ru.otus4.service;

import java.util.HashMap;
import java.util.Map;


/**
 * Хранилище денег.
 */
public class Currency {

    private final Map<String, Integer> cells = new HashMap<>(7, 1.0f);

    public long fillCells(Map<String, Integer> cell) {
        return cell.keySet().stream()
                .filter(cells::containsKey)
                .map(name -> cells.put(name, cell.get(name))).count();
    }


    protected Map<String, Integer> getCells() {
        return cells;
    }
}
