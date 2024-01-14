package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
    private final EntityClassMetaData<T> entityClassMetaData;


    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    T entity = entityClassMetaData.getConstructor().newInstance();
                    for (Field fld : entityClassMetaData.getAllFields()) {
                        Object v = rs.getObject(fld.getName());
                        fld.setAccessible(true);
                        fld.set(entity, v);
                    }
                    return entity;
                }
                return null;
            } catch (Exception ex) {
                throw new DataTemplateException(ex);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var entityList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    T entity = entityClassMetaData.getConstructor().newInstance();
                    for (Field fld : entityClassMetaData.getAllFields()) {
                        Object value = rs.getObject(fld.getName());
                        fld.setAccessible(true);
                        fld.set(entity, value);
                    }
                    entityList.add(entity);
                }
                return entityList;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T entity) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    getFieldValues(entityClassMetaData.getFieldsWithoutId(), entity));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T entity) {
        try {
            List<Object> params = new ArrayList<>(List.of(getFieldValues(entityClassMetaData.getFieldsWithoutId(), entity)));
            params.add(extractValue(entity, entityClassMetaData.getIdField()));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getFieldValues(List<Field> fields, T entity) {
        return fields.stream().map(f -> extractValue(entity, f)).toList();
    }

    private Object extractValue(T entity, Field field) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
