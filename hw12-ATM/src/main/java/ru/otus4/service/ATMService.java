package ru.otus4.service;

import java.util.List;

public interface ATMService {

    List<Integer> getSumForClientRequest(int sum);

    String fillClientsCells(int sum);

    String encashmentFill(String nameOfNominal, Integer count);

    int encashmentPull();

}
