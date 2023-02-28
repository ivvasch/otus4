package ru.otus4.controller;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus4.dto.ClientDTO;
import ru.otus4.model.Client;
import ru.otus4.service.ClientService;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/")
public class ClientController {

    private final ClientService clientService;
    private static final String CLIENTS = "clients";

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        log.info("Start index page");
        model.addAttribute("client", new Client(null, null, null, null, false));
        return "index";
    }

    @GetMapping("/api/random")
    public String addRandomUsers(Model model) {
        log.info("New clients will be add to DB");
        clientService.addRandomUsers();
        return index(model);
    }

    @GetMapping("/api/client/")
    public String getClientById(@RequestParam("id") String id, Model model) {
        log.info("Get client by id");
        if (StringUtils.isBlank(id)) {
            return index(model);
        }
        Client client = clientService.getClientById(id);
        Set<Client> clients = new HashSet<>();
        clients.add(client);
        model.addAttribute(CLIENTS, clients);
        return "client";
    }

    @PostMapping("/api/client/")
    public String getClientByName(@RequestParam("name") String name, Model model) {
        log.info("Get Clients by name");
        if (StringUtils.isBlank(name)) {
            return index(model);
        }
        Set<Client> clients = clientService.getClientByName(name);
        model.addAttribute(CLIENTS, clients);
        return "client";
    }

    @GetMapping("/api/client")
    public String getAllClients(Model model) {
        log.info("Get all clients");
        Set<Client> clients = clientService.getAllClients();
        model.addAttribute(CLIENTS, clients);
        return "allclient";
    }

    @PostMapping("/api/client")
    public String saveNewClient(@ModelAttribute(value = "clientdto") ClientDTO clientDTO, Model model) {
        log.info("Save new client to DB");
        if (clientDTO.getName() != null) {
            model.addAttribute("clientdto", clientDTO);
            clientService.save(clientDTO);
            return "redirect:/api/client";

        }
        return "addclient";
    }
}
