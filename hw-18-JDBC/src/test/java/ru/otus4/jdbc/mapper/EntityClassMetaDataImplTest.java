package ru.otus4.jdbc.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus4.crm.model.Client;

class EntityClassMetaDataImplTest  {
    Client client = new Client();
    EntityClassMetaData<Client> entityClassMetaData = new EntityClassMetaDataImpl<>(client);
    @Test
    void getName() {
        String name = entityClassMetaData.getName();
        System.out.println(name);
        Assertions.assertEquals("Client", name);
    }

    @Test
    void getConstructor() {
        String nameOfType = entityClassMetaData.getConstructor().getName();
        int i = nameOfType.lastIndexOf(".");
        String substring = nameOfType.substring(i + 1);
        Assertions.assertEquals("Client", substring);
    }

    @Test
    void getIdField() {
    }

    @Test
    void getAllFields() {
    }

    @Test
    void getFieldsWithoutId() {
    }
}
