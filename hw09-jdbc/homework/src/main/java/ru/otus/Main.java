package ru.otus;

import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.impl.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.impl.EntitySQLMetaDataImpl;

public class Main {
    public static void main(String[] args){
        EntityClassMetaData<Manager> entityClassMetaData  = new EntityClassMetaDataImpl<>(Manager.class);
        System.out.println(entityClassMetaData.getName());

        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);
        System.out.println(entitySQLMetaData.getSelectAllSql());
        System.out.println(entitySQLMetaData.getSelectByIdSql());
        System.out.println(entitySQLMetaData.getInsertSql());
        System.out.println(entitySQLMetaData.getUpdateSql());
    }
}
