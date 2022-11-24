package ru.otus4.service;

public interface ATMService {

    String getSumForClientRequest(int sum);

    String fillClientsCells(int sum);

    String encashmentFill(String nameOfNominal, Integer count);

    int encashmentPull();

}
