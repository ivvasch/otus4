package ru.otus4.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus4.repository.DataTemplate;
import ru.otus4.sessionmanager.TransactionManager;
import ru.otus4.model.Client;
import ru.otus4.model.Phone;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            String address = clientOptional.get().getAddress().getAddress();
            List<Phone> phones = clientOptional.get().getPhones();
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            for (Client client : clientList) {
                String address = client.getAddress().getAddress();
                String phones = client.getPhones().toString();
            }
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }

    @Override
    public Optional<Client> getClientByUsername(String username) {
        List<Client> all = findAll();
        return all.stream().filter(client -> username.equals(client.getName())).findFirst();
    }

    @Override
    public Optional<Client> findRandomClient() {
        Random r = new Random();
        List<Client> all = findAll();
        return all.stream().skip(r.nextInt(all.size() - 1)).findFirst();
    }
}
