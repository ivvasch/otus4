package ru.otus4.service;

import ru.otus4.enums.NominalsEnum;

import java.util.Map;

public class ATM {
    private final Deposit deposit;

    public ATM(Deposit deposit) {
        this.deposit = deposit;
        fillTheATMFirst();
    }

    private void fillTheATMFirst() {
        Map<String, Integer> cells = deposit.getCells();
        System.out.println("Начальное пополнение банкомата ");
        cells.put(NominalsEnum.NOMIN_50.name(), 5);
        cells.put(NominalsEnum.NOMIN_100.name(), 5);
        cells.put(NominalsEnum.NOMIN_200.name(), 5);
        cells.put(NominalsEnum.NOMIN_500.name(), 5);
        cells.put(NominalsEnum.NOMIN_1000.name(), 5);
        cells.put(NominalsEnum.NOMIN_2000.name(), 5);
        cells.put(NominalsEnum.NOMIN_5000.name(), 5);
    }

    public void fillTheATM(Map<String, Integer> nominals) {
        long count = deposit.fillCells(nominals);
        System.out.println(" Пополнено " + count + " ячеек");
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public int getNominals(String key) {
        return deposit.getCells().get(key);
    }

    public void setNominals(String key, int count) {
        Map<String, Integer> cells = deposit.getCells();
        if (cells.containsKey(key)){
                cells.put(key, count);
        }
    }
}
