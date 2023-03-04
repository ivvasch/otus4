package ru.otus4.service;

import org.springframework.stereotype.Service;
import ru.otus4.dto.ClientDTO;
import ru.otus4.enums.EnumAddress;
import ru.otus4.enums.EnumName;
import ru.otus4.model.Address;
import ru.otus4.model.Client;
import ru.otus4.model.Phone;
import ru.otus4.repository.ClientRepository;
import ru.otus4.sessionmanager.TransactionClient;

import java.util.*;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final TransactionClient transactionClient;
    private Random random = new Random();

    public ClientService(ClientRepository clientRepository, TransactionClient transactionClient) {
        this.clientRepository = clientRepository;
        this.transactionClient = transactionClient;
    }

    public Client getClientById(String id) {
        Optional<Client> clientById = clientRepository.findClientById(id);
        return clientById.isPresent() ? clientById.get() : null;
    }

    public Set<Client> getAllClients() {
        Set<Client> allClients = clientRepository.findAll();
        return allClients;
    }

    public void save(ClientDTO clientDTO) {
        transactionClient.doInTransaction(() -> {
            if (clientDTO != null) {
                Client clientFromDTO = getClientFromDTO(clientDTO);
                clientRepository.save(clientFromDTO);
            }
            return null;
        });
    }

    private Client getClientFromDTO(ClientDTO clientDTO) {
        String name = clientDTO.getName();
        Address address = clientDTO.getAddress();
        Phone phone = clientDTO.getPhone();
        UUID uuid = UUID.randomUUID();
        return new Client(uuid.toString(), name, address, Collections.singleton(phone), true);
    }

    public Set<Client> getClientByName(String name) {
        Set<Client> clientsByName = clientRepository.findClientsByName(name);
        return clientsByName;
    }

    public void addRandomUsers() {
        Set<Client> clients = new HashSet<>();
        for (int i = 0; i < 15; i++) {
            Client client = new Client(i + "_" + new Random().nextInt(), getRandomName(), getRandomAddress(), getRandomPhone(), true);
            clients.add(client);
        }
        transactionClient.doInTransaction(() -> clientRepository.saveAll(clients));
    }

    private Address getRandomAddress() {
        EnumAddress[] values = EnumAddress.values();
        int i = random.nextInt(values.length);
        String address = values[i].getStreet();
        return new Address(address + ", " + i);
    }

    private Set<Phone> getRandomPhone() {
        StringBuilder number = new StringBuilder("+");
        for (int i = 0; i < 10; i++) {
            int n = random.nextInt(10);
            number.append(n);
        }
        Phone phone = new Phone(number.toString());
        Set<Phone> phones = Collections.singleton(phone);
        return phones;
    }

    private String getRandomName() {
        int n = random.nextInt(EnumName.values().length);
        return EnumName.values()[n].getName();
    }
}
