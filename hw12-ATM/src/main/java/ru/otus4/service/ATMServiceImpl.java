package ru.otus4.service;

import ru.otus4.enums.NominalsEnum;

import java.util.*;

public class ATMServiceImpl implements ATMService{
    private final ATM atm;
    public ATMServiceImpl(ATM atm) {
        this.atm = atm;
    }

    @Override
    public List<Integer> getSumForClientRequest(int sum) {
        List<Integer> nominalsList = new ArrayList<>();
        List<NominalsEnum> nominalsEnums = Arrays.stream(NominalsEnum.values())
                .sorted(Comparator.reverseOrder())
                .filter(nominals -> sum >= nominals.getNominals())
                .toList();
        int total = sum;
        int balance = sum;
        int allAmountATM = 0;
        for (NominalsEnum nominals : nominalsEnums) {
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
                nominalsList.add(nominals.getNominals());
            }
            allAmountATM = allAmountATM + total * nominals.getNominals();
            total = balance;
        }
        if (allAmountATM < sum) {
            throw new RuntimeException("Not enough money");
        }
        return nominalsList;

    }

    @Override
    public String fillClientsCells(int sum) {
        Map<String, Integer> fillClientCells = new HashMap<>();
        int total = sum;
        NominalsEnum[] values = NominalsEnum.values();
        for (NominalsEnum value : values) {
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
        for (NominalsEnum value : NominalsEnum.values()) {
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
        for (NominalsEnum value : NominalsEnum.values()) {
            total = total + atm.getNominals(value.name()) * value.getNominals();
            atm.setNominals(value.name(), 0);
        }
        return total;
    }

    public String balanceATM() {
        StringBuilder stringBuilder = new StringBuilder();
        int sum = 0;
        stringBuilder.append("--------------------")
                .append("\n")
                .append("Остаток купюр в АТМ");
        for (NominalsEnum value : NominalsEnum.values()) {
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
        return sum % NominalsEnum.NOMIN_50.getNominals() == 0;
    }
}
