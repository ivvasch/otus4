package ru.otus4.enums;

public enum CurrencyEnum {
    CURR_50(50),

    CURR_100(100),

    CURR_200(200),

    CURR_500(500),

    CURR_1000(1000),

    CURR_2000(2000),

    CURR_5000(5000);

    private final int nominals;

    CurrencyEnum(int nominals) {
        this.nominals = nominals;
    }

    public int getNominals() {
        return nominals;
    }
}
