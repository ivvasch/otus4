package ru.otus4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.cfg.Configuration;
import ru.otus4.core.repository.DataTemplateHibernate;
import ru.otus4.core.repository.HibernateUtils;
import ru.otus4.core.sessionmanager.TransactionManagerHibernate;
import ru.otus4.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus4.crm.model.Address;
import ru.otus4.crm.model.Client;
import ru.otus4.crm.model.Phone;
import ru.otus4.crm.service.DbServiceClientImpl;
import ru.otus4.dao.InDBService;
import ru.otus4.server.UsersWebServer;
import ru.otus4.server.UsersWebServerWithBasicSecurity;
import ru.otus4.services.TemplateProcessor;
import ru.otus4.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Phone.class, Address.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        LoginService loginService = new InDBService(dbServiceClient);

        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, dbServiceClient, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
