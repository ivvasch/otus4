package ru.otus4.service;

import ru.otus4.enums.CurrencyEnum;

import java.util.Map;

public class ATM {
    private final Currency currency;

    public ATM(Currency currency) {
        this.currency = currency;
        fillTheATMFirst();
    }

    private void fillTheATMFirst() {
        Map<String, Integer> cells = currency.getCells();
        System.out.println("Начальное пополнение банкомата ");
        cells.put(CurrencyEnum.CURR_50.name(), 5);
        cells.put(CurrencyEnum.CURR_100.name(), 5);
        cells.put(CurrencyEnum.CURR_200.name(), 5);
        cells.put(CurrencyEnum.CURR_500.name(), 5);
        cells.put(CurrencyEnum.CURR_1000.name(), 5);
        cells.put(CurrencyEnum.CURR_2000.name(), 5);
        cells.put(CurrencyEnum.CURR_5000.name(), 5);
    }

    public void fillTheATM(Map<String, Integer> nominals) {
        long count = currency.fillCells(nominals);
        System.out.println(" Пополнено " + count + " ячеек");
    }

    public Currency getCurrency() {
        return currency;
    }

    public int getNominals(String key) {
        return currency.getCells().get(key);
    }

    public void setNominals(String key, int count) {
        Map<String, Integer> cells = currency.getCells();
        if (cells.containsKey(key)){
                cells.put(key, count);
        }
    }
}
