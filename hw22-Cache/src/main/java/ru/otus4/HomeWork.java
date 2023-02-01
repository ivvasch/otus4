package ru.otus4;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus4.core.repository.executor.DbExecutorImpl;
import ru.otus4.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus4.crm.datasource.DriverManagerDataSource;
import ru.otus4.crm.model.Client;
import ru.otus4.crm.model.Manager;
import ru.otus4.crm.repository.DataTemplateJdbc;
import ru.otus4.crm.service.DbServiceClientImpl;
import ru.otus4.crm.service.DbServiceManagerImpl;
import ru.otus4.jdbc.mapper.EntityClassMetaData;
import ru.otus4.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus4.jdbc.mapper.EntitySQLMetaData;
import ru.otus4.jdbc.mapper.EntitySQLMetaDataImpl;

import javax.sql.DataSource;
import java.util.Date;
import java.util.stream.IntStream;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        Client client = new Client();
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(client);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient); //реализация DataTemplate, универсальная

// Код дальше должен остаться
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
        var client28 = dbServiceClient.saveClient(new Client(28L,"dbService28"));
// --------------------проверка работы с кэш----------------------------------------------------
        long startTime = new Date().getTime();
        get10Clients(dbServiceClient);
        long endTime = new Date().getTime();
        System.out.println("firstTime >>> " + (endTime - startTime)); // 118ms

        startTime = new Date().getTime();
        get10Clients(dbServiceClient);
        endTime = new Date().getTime();
        System.out.println("secondTime >>> " + (endTime - startTime)); // 74ms
// ---------------------------------------------------------------------------------------------
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);

// Сделайте тоже самое с классом Manager (для него надо сделать свою таблицу)

        Manager manager = new Manager();
        EntityClassMetaData<Manager> entityClassMetaDataManager = new EntityClassMetaDataImpl<>(manager);
        EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(entityClassMetaDataManager);
        var dataTemplateManager = new DataTemplateJdbc<Manager>(dbExecutor, entitySQLMetaDataManager);

        var dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);
        dbServiceManager.saveManager(new Manager("ManagerFirst"));

        var managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"));
        var managerThird = dbServiceManager.saveManager(new Manager(16L, "Manager16", "param"));
        var managerSecondSelected = dbServiceManager.getManager(managerSecond.getNo())
                .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getNo()));
        log.info("managerSecondSelected:{}", managerSecondSelected);
    }

    private static void get10Clients(DbServiceClientImpl dbServiceClient) {
        IntStream.range(0, 100).forEach(dbServiceClient::getClient);
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
