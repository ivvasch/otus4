package ru.otus4.enums;

public enum NominalsEnum {
    NOMIN_50(50),

    NOMIN_100(100),

    NOMIN_200(200),

    NOMIN_500(500),

    NOMIN_1000(1000),

    NOMIN_2000(2000),

    NOMIN_5000(5000);

    private final int nominals;

    NominalsEnum(int nominals) {
        this.nominals = nominals;
    }

    public int getNominals() {
        return nominals;
    }
}
