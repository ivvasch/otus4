package ru.otus4.service;

import java.util.HashMap;
import java.util.Map;


/**
 * Хранилище денег.
 */
public class Deposit {

    private final Map<String, Integer> cells = new HashMap<>(7, 1.0f);

    public long fillCells(Map<String, Integer> cell) {
        long count = cell.keySet().stream()
                .filter(cells::containsKey)
                .map(name -> cells.put(name, cell.get(name))).count();
        if (count == 0) {
            System.out.println("Попытка добавить несуществующий номинал");
        }
        return count;
    }


    protected Map<String, Integer> getCells() {
        return cells;
    }
}
