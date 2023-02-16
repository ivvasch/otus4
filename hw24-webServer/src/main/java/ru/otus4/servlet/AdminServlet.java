package ru.otus4.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.StringUtil;
import ru.otus4.model.Address;
import ru.otus4.model.Client;
import ru.otus4.model.Phone;
import ru.otus4.services.DBServiceClient;
import ru.otus4.services.TemplateProcessor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String  CLIENTS = "clients";

    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public AdminServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        if ("all".equals(req.getParameter("allUsers")) || "all".equals(req.getAttribute("allUsers"))) {
            List<Client> clients = dbServiceClient.findAll();
            paramsMap.put(CLIENTS, clients);
        }
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");

        Client client = new Client();
        Address addr = new Address();
        Phone ph = new Phone();
        if (!StringUtil.isBlank(name)) {
            client.setName(name);
        }
        if (!StringUtil.isBlank(address)) {
            addr.setAddress(address);
            client.setAddress(addr);
        }
        if (!StringUtil.isBlank(phone)) {
            ph.setPhone(phone);
            client.setPhones(Collections.singletonList(ph));
        }
        dbServiceClient.saveClient(client);
        req.setAttribute("allUsers", "all");
        doGet(req, resp);

    }

}
