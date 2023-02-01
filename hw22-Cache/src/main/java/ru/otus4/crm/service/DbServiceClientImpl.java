package ru.otus4.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus4.cachehw.MyCache;
import ru.otus4.core.repository.DataTemplate;
import ru.otus4.core.sessionmanager.TransactionRunner;
import ru.otus4.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> dataTemplate;
    private final TransactionRunner transactionRunner;
    private MyCache<Long, Client> myCache = new MyCache<>();


    public DbServiceClientImpl(TransactionRunner transactionRunner, DataTemplate<Client> dataTemplate) {
        this.transactionRunner = transactionRunner;
        this.dataTemplate = dataTemplate;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null || getClient(client.getId()).isEmpty()) {
                var clientId = dataTemplate.insert(connection, client);
                var createdClient = new Client(clientId, client.getName());
                log.info("created client: {}", createdClient);
                return createdClient;
            }
            dataTemplate.update(connection, client);
            log.info("updated client: {}", client);
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client cl = myCache.get(id);
        if (cl == null) {
            Optional<Client> optionalClient = transactionRunner.doInTransaction(connection -> dataTemplate.findById(connection, id));
            optionalClient.ifPresent(client -> myCache.put(id, client));
            return optionalClient;
        }
        return Optional.of(cl);
    }

    @Override
    public List<Client> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var clientList = dataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }
}
