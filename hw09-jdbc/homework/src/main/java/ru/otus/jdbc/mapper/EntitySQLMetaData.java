package ru.otus.jdbc.mapper;

import java.util.Optional;

/**
 * Создает SQL - запросы
 */
public interface EntitySQLMetaData{
    String getSelectAllSql();

    String getSelectByIdSql();

    String getInsertSql();

    String getUpdateSql();
}
