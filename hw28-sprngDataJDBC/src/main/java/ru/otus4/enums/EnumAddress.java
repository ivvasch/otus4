package ru.otus4.enums;

public enum EnumAddress {

    KASHIRSKAYA("Kashirskaya"),
    POLTAVSKAYA("Poltavskaya"),
    B_NABEREJNAYA("B_naberejnaya"),
    LUBYANKA("Lubyanka"),
    LITVINOVA("Litvinova"),
    MIHAYLOVSKAYA("Mihaylovskaya");

    private final String street;

    EnumAddress(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }
}
