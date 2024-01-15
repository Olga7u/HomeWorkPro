package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    private List<String> getFieldNames(List<Field> fields) {
        return fields.stream().map(Field::getName).toList();
    }

    private String getFieldNamesForInsert(List<Field> fields) {
        StringBuilder fieldsStr = new StringBuilder();
        for (String item : getFieldNames(fields)) {
            if (!fieldsStr.isEmpty()) {
                fieldsStr.append(", ");
            }
            fieldsStr.append(item);
        }
        return fieldsStr.toString();
    }

    private String getFieldValuesForInsert(List<Field> fields) {
        StringBuilder fieldsStr = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            if (!fieldsStr.isEmpty()) {
                fieldsStr.append(", ");
            }
            fieldsStr.append("?");
        }
        return fieldsStr.toString();
    }

    private String getFieldNamesForUpdate(List<Field> fields) {
        StringBuilder fieldsStr = new StringBuilder();
        for (String item : getFieldNames(fields)) {
            if (!fieldsStr.isEmpty()) {
                fieldsStr.append(", ");
            }
            fieldsStr.append(item).append("=?");
        }
        return fieldsStr.toString();
    }

    @Override
    public String getSelectAllSql() {
        return "select " + getFieldNamesForInsert(entityClassMetaData.getAllFields())
                + " from " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectAllSql() + " where " + entityClassMetaData.getIdField().getName() + "=?";
    }

    @Override
    public String getInsertSql() {
        return "insert into " + entityClassMetaData.getName()
                + "(" + getFieldNamesForInsert(entityClassMetaData.getFieldsWithoutId())
                + ") values(" + getFieldValuesForInsert(entityClassMetaData.getFieldsWithoutId()) + ")";
    }

    @Override
    public String getUpdateSql() {
        return "update " + entityClassMetaData.getName()
                + " set " + getFieldNamesForUpdate(entityClassMetaData.getFieldsWithoutId())
                + " where id=?";
    }
}
