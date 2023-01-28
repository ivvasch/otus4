package ru.otus4.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }
    @Override
    public String getSelectAllSql() {
        String model = entityClassMetaData.getConstructor().getName();
        model = getTypeOfParameter(model);
        return "SELECT * FROM " + model;
    }

    private String getTypeOfParameter(String nameOfType) {
        int i = nameOfType.lastIndexOf(".");
        return nameOfType.substring(i + 1).toLowerCase();
    }

    @Override
    public String getSelectByIdSql() {
        String model = entityClassMetaData.getConstructor().getName();
        model = getTypeOfParameter(model);
        Field idField = entityClassMetaData.getIdField();
        String fieldId = getTypeOfParameter(idField.toString());
        return "SELECT * FROM " + model + " WHERE " + fieldId + " = ?";
    }

    @Override
    public String getInsertSql() {
        StringBuilder builder = new StringBuilder();
        StringBuilder parametersBuilder = new StringBuilder();
        List<Field> allFields = entityClassMetaData.getFieldsWithoutId();
        allFields.forEach(f -> {
            parametersBuilder.append("?, ");
            builder.append(getTypeOfParameter(f.toString())).append(", ");
        });
        int i = parametersBuilder.toString().lastIndexOf(",");
        int k = builder.toString().lastIndexOf(",");
        String insertValues = parametersBuilder.substring(0, i);
        String insertParams = builder.substring(0, k);
        String model = entityClassMetaData.getConstructor().getName();
        model = getTypeOfParameter(model);
        return "insert into " + model + "("+ insertParams + ") values (" + insertValues + ");";
    }

    @Override
    public String getUpdateSql() {
        StringBuilder builder = new StringBuilder();
        String model = entityClassMetaData.getConstructor().getName();
        model = getTypeOfParameter(model);
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        fieldsWithoutId.forEach(f -> builder.append(getTypeOfParameter(f.toString())).append("=").append("?").append(","));
        Field idField = entityClassMetaData.getIdField();
        int k = builder.toString().lastIndexOf(",");
        String setValues = builder.substring(0, k);
        return "UPDATE " + model + " SET " + setValues + " where " + idField.getName() + " = ?;";
    }
}
