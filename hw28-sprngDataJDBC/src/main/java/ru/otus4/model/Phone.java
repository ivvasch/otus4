package ru.otus4.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public class Phone {

    private final String phone;
    @Id
    private final String clientId;

    public Phone(String phone) {
        this(phone, null);
    }
    @PersistenceCreator
    public Phone(String phone, String clientId) {
        this.phone = phone;
        this.clientId = clientId;
    }


    public String getPhone() {
        return phone;
    }

    public String getClientId() {
        return clientId;
    }
}
