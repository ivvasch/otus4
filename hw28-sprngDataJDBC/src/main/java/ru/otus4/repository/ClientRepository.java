package ru.otus4.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus4.model.Client;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {
    Optional<Client> findClientById(String id);

    Set<Client> findClientsByName(String name);

    Set<Client> findAll();

    Client save(Client client);

}
