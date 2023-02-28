package ru.otus4.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "addresses")
public class Address {
    private final String address;
    @Id
    private final String clientId;

    public Address(String address) {
        this(address, null);
    }

    @PersistenceCreator
    public Address(String address, String clientId) {
        this.address = address;
        this.clientId = clientId;
    }


    public String getAddress() {
        return address;
    }

    public String getClientId() {
        return clientId;
    }
}
