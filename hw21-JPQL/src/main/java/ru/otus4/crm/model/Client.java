package ru.otus4.crm.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        addClientToPhone(phones);

    }

    private void addClientToPhone(List<Phone> phones) {
        for (Phone phone : phones) {
            phone.setClient(this);
        }
    }

    @Override
    public Client clone() {
        Client client = new Client(this.id, this.name);
        if (this.address != null) {
            client.setAddress(new Address(this.address.getId(), this.address.getAddress()));
        }
        if (this.phones != null) {
            client.setPhones(getListPhone(this.phones));
        }
        return client;
    }

    private List<Phone> getListPhone(List<Phone> phone) {
        List<Phone> phones = new ArrayList<>();
        phone.forEach(ph -> {
            Phone phon = new Phone(ph.getId(), ph.getPhone());
            phones.add(phon);
        });
        return phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phone) {
        for (Phone ph : phone) {
            ph.setClient(this);
        }
        this.phones = phone;
    }
}
