package ru.otus4.model;


import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table(name = "client")
public class Client implements Persistable<String> {

    @Id
    private final String id;

    @NonNull
    private final String name;

    @NonNull
    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @NonNull
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    @Transient
    private final boolean isNew;



    public Client(String id, String name, Address address, Set<Phone> phones, boolean isNew) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.isNew = isNew;
    }
    @PersistenceCreator
    public Client(String id, @NonNull String name, @NonNull Address address, @NonNull Set<Phone> phones) {
        this(id, name, address, phones, false);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }


    public String getName() {
        return name;
    }


    public Address getAddress() {
        return address;
    }


    public Set<Phone> getPhones() {
        return phones;
    }

}
