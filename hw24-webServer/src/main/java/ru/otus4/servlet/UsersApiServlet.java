package ru.otus4.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus4.crm.model.Address;
import ru.otus4.crm.model.Client;
import ru.otus4.crm.model.Phone;
import ru.otus4.crm.service.DBServiceClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UsersApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public UsersApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = dbServiceClient.getClient(extractIdFromRequest(request)).orElse(null);
        Client clientForJson = getClientForJson(client);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(clientForJson));
    }

    private static Client getClientForJson(Client client) {
        Client clientForJson = new Client();
        clientForJson.setPassword(client.getPassword());
        Address address = new Address();
        address.setAddress(client.getAddress().getAddress());
        address.setId(client.getAddress().getId());
        List<Phone> phoneList = new ArrayList<>();
        List<Phone> phones = client.getPhones();
        if (!phones.isEmpty()) {
            phones.forEach(phoneList::add);
        }
        clientForJson.setAddress(address);
        clientForJson.setPhones(phoneList);
        clientForJson.setName(client.getName());
        clientForJson.setId(client.getId());
        return clientForJson;
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
