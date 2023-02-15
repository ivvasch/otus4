package ru.otus4.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus4.crm.service.DBServiceClient;
import ru.otus4.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_RANDOM_CLIENT = "randomClient";

//    private final DataTemplate userDao;
    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        dbServiceClient.findRandomClient().ifPresent(randomClient -> paramsMap.put(TEMPLATE_ATTR_RANDOM_CLIENT, randomClient));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}
