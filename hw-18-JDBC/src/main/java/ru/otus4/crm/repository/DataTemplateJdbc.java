package ru.otus4.crm.repository;

import ru.otus4.core.repository.DataTemplate;
import ru.otus4.core.repository.DataTemplateException;
import ru.otus4.core.repository.executor.DbExecutor;
import ru.otus4.crm.model.Client;
import ru.otus4.crm.model.Manager;
import ru.otus4.jdbc.mapper.EntityClassMetaData;
import ru.otus4.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus4.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        String selectAllSql = entitySQLMetaData.getSelectAllSql();
        int i = selectAllSql.lastIndexOf(" ");
        String substring = selectAllSql.substring(i + 1);
        return (Optional<T>) dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    if (substring.equals("client")) {
                        return new Client(rs.getLong("id"), rs.getString("name"));
                    } else {
                        return new Manager(rs.getLong("no")
                                , rs.getString("name")
                                , rs.getString("param1"));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        String selectAllSql = entitySQLMetaData.getSelectAllSql();
        int i = selectAllSql.lastIndexOf(" ");
        String substring = selectAllSql.substring(i + 1);
        return dbExecutor.executeSelect(connection, selectAllSql, Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {

                    if (substring.equals("client")) {
                        clientList.add((T) new Client(rs.getLong("id"), rs.getString("name")));
                    } else {

                        clientList.add((T) new Manager(rs.getLong("no")
                                , rs.getString("name")
                                , rs.getString("param1")));
                    }
                    }
                return clientList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        EntityClassMetaData<T> classMetaData = new EntityClassMetaDataImpl<>(client);
        List<Field> fieldsWithoutId = classMetaData.getFieldsWithoutId();
        List<Object> listData = new ArrayList<>();
        fieldsWithoutId.forEach(f -> {
            f.setAccessible(true);
            try {
                listData.add(f.get(client));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    listData);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        EntityClassMetaData<T> classMetaData = new EntityClassMetaDataImpl<>(client);
        List<Field> fieldsWithoutId = classMetaData.getFieldsWithoutId();
        List<Object> listData = new ArrayList<>();
        fieldsWithoutId.forEach(f -> {
            f.setAccessible(true);
            try {
                listData.add(f.get(client));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        Field idField = classMetaData.getIdField();
        idField.setAccessible(true);
        try {
            listData.add(idField.get(client));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    listData);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
