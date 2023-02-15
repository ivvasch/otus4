package ru.otus4.dao;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.RolePrincipal;
import org.eclipse.jetty.security.UserPrincipal;
import org.eclipse.jetty.util.security.Password;
import ru.otus4.crm.model.Address;
import ru.otus4.crm.model.Client;
import ru.otus4.crm.service.DBServiceClient;
import ru.otus4.crm.service.DbServiceClientImpl;

import java.util.List;
import java.util.Optional;

public class InDBService extends AbstractLoginService {
    private DBServiceClient dbServiceClient;
    public InDBService(DbServiceClientImpl dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
        createSomeClient();
    }

    private void createSomeClient() {
        Client client = new Client("user1");
        Address address = new Address();
        address.setAddress("Kolomenskaya");
        client.setAddress(address);
        client.setPassword("user");
        Client client2 = new Client("Ivan");
        Address address2 = new Address();
        address.setAddress("Kolomenskaya");
        client2.setAddress(address2);
        client2.setPassword("11111");
        dbServiceClient.saveClient(client);
        dbServiceClient.saveClient(client2);

    }

    @Override
    protected List<RolePrincipal> loadRoleInfo(UserPrincipal user) {
        return List.of(new RolePrincipal("user"));
    }

    @Override
    protected UserPrincipal loadUserInfo(String username) {
        Optional<Client> client = dbServiceClient.getClientByUsername(username);
        return client.map(c -> new UserPrincipal(c.getName(), new Password(c.getPassword()))).orElse(null);
    }
}
