package ru.otus4.dto;

import lombok.*;
import ru.otus4.model.Address;
import ru.otus4.model.Phone;


@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private String name;
    private Address address;
    private Phone phone;

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

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
