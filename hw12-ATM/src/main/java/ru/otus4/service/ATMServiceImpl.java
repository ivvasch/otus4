package ru.otus4.service;

import ru.otus4.enums.CurrencyEnum;

import java.util.*;

public class ATMServiceImpl implements ATMService{
    private final ATM atm;
    public ATMServiceImpl(ATM atm) {
        this.atm = atm;
    }

    @Override
    public String getSumForClientRequest(int sum) {
        StringBuilder stringBuilder = new StringBuilder();
        List<CurrencyEnum> currencyEnums = Arrays.stream(CurrencyEnum.values())
                .sorted(Comparator.reverseOrder())
                .filter(nominals -> sum >= nominals.getNominals())
                .toList();
        int total = sum;
        int balance = sum;
        int allAmountATM = 0;
        for (CurrencyEnum nominals : currencyEnums) {
            total = total / nominals.getNominals();
            if (total <= atm.getNominals(nominals.name())) {
                balance = balance % nominals.getNominals();
            } else {
                int diff = total - atm.getNominals(nominals.name());
                total = atm.getNominals(nominals.name());
                int surplus = diff * nominals.getNominals();
                balance = (balance % nominals.getNominals()) + surplus;

            }
            if ((total != 0 || balance == 0) && total != balance) {
                atm.setNominals(nominals.name(), atm.getNominals(nominals.name()) - total);
                appender(stringBuilder, total, nominals);
            }
            allAmountATM = allAmountATM + total * nominals.getNominals();
            total = balance;
        }
        if (allAmountATM < sum) {
            throw new RuntimeException("Not enough money");
        }
        return stringBuilder.toString();

    }

    @Override
    public String fillClientsCells(int sum) {
        Map<String, Integer> fillClientCells = new HashMap<>();
        int total = sum;
        CurrencyEnum[] values = CurrencyEnum.values();
        for (CurrencyEnum value : values) {
            if (total != 0) {
                int nominals = value.getNominals();
                int count = total / nominals;
                int balance = nominals * count;
                total = total - balance;
                fillClientCells.put(value.name(), atm.getNominals(value.name()) + count);
                atm.fillTheATM(fillClientCells);
            }
            break;
        }
        return " Ваш счет пополнен на " + sum;
    }

    @Override
    public String encashmentFill(String nameOfNominal, Integer count) {
        Map<String, Integer> encashmentFillMap = new HashMap<>();
        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.name().equals(nameOfNominal)) {
                encashmentFillMap.put(value.name(), count);
                atm.fillTheATM(encashmentFillMap);
            }
        }
        return "Купюры загружены ";
    }

    @Override
    public int encashmentPull() {
        int total = 0;
        for (CurrencyEnum value : CurrencyEnum.values()) {
            total = total + atm.getNominals(value.name()) * value.getNominals();
            atm.setNominals(value.name(), 0);
        }
        return total;
    }

    private void appender(StringBuilder stringBuilder, int total, CurrencyEnum nominals) {
            stringBuilder.append(nominals.getNominals())
                    .append(" рублей")
                    .append(" - ")
                    .append(total)
                    .append("\n");
        }


    public String balanceATM() {
        StringBuilder stringBuilder = new StringBuilder();
        int sum = 0;
        stringBuilder.append("--------------------")
                .append("\n")
                .append("Остаток купюр в АТМ");
        for (CurrencyEnum value : CurrencyEnum.values()) {
            stringBuilder.append("\n")
                    .append(value.getNominals())
                    .append(" - ")
                    .append(atm.getNominals(value.name()));
            sum = sum + value.getNominals() * atm.getNominals(value.name());
        }
        stringBuilder.append("\n")
                .append("Остаток - ")
                .append(sum);
        return stringBuilder.toString();
    }

    public boolean checkValidSum(int sum) {
        return sum % CurrencyEnum.CURR_50.getNominals() == 0;
    }
}
