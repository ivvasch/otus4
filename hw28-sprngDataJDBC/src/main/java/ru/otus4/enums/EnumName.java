package ru.otus4.enums;

public enum EnumName {
    ELENA(1, "Elena"),
    SERGEY(2, "Sergey"),
    PETR(3, "Petr"),
    IGOR(4, "Igor"),
    IVAN(5, "Ivan"),
    MIHAIL(6, "Mihail"),
    OLGA(7, "Olga"),
    INNA(8, "Inna"),
    SVETLANA(9, "Svetlana");

    private final String name;
    private int code;

    EnumName(int code, String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
