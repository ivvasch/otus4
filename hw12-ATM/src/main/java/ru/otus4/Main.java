package ru.otus4;

import ru.otus4.service.Currency;
import ru.otus4.enums.CurrencyEnum;
import ru.otus4.service.ATM;
import ru.otus4.service.ATMServiceImpl;

public class Main {
    private static ATMServiceImpl service;
    public static void main(String[] args) throws InterruptedException {
        Currency currency = new Currency();
        ATM atm = new ATM(currency);
        service = new ATMServiceImpl(atm);

        getBalanceATM();

        Thread.sleep(6000);
        int sum;
        System.out.println("Пополнение счета клиента на 13250р");
        sum = 13250;
        String fill = service.fillClientsCells(sum);
        System.out.println(fill);
        System.out.println("--------------------\n");

        getBalanceATM();

        Thread.sleep(6000);
        sum = 14350;
        System.out.println("Выдача денег клиенту " + sum);
        boolean checked = service.checkValidSum(sum);
        if (checked) {
            String sumForClientRequest = service.getSumForClientRequest(sum);
            System.out.println(sumForClientRequest);
        }
        System.out.println("--------------------\n");

        getBalanceATM();

        Thread.sleep(6000);
        System.out.println("Заполнение банкомата");
        String encashmentFill = service.encashmentFill(CurrencyEnum.CURR_1000.name(), 5000);
        System.out.println(encashmentFill);
        System.out.println("--------------------\n");

        getBalanceATM();

        Thread.sleep(6000);
        System.out.println("Забор денег из банкомата ");
        int encashmentPull = service.encashmentPull();
        System.out.println("Забор денег " + encashmentPull);
        System.out.println("--------------------\n");

        getBalanceATM();
    }

    public static void getBalanceATM() {
        System.out.println("Остаток купюр в банкомате");
        String balanceATM = service.balanceATM();
        System.out.println(balanceATM);
        System.out.println("-------------------- \n");


    }
}
