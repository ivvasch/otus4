package ru.otus4.services;

import ru.otus4.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();

    Optional<Client> getClientByUsername(String username);

    Optional<Client> findRandomClient();
}
