package ru.otus.jdbc.mapper.impl;

import ru.otus.annotations.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> cls;

    public <C extends Class<T>> EntityClassMetaDataImpl(C cls) {
        this.cls = cls;
    }

    @Override
    public String getName() {
        return cls.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() throws NoSuchMethodException {
        return cls.getConstructor();
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(cls.getDeclaredFields()).filter(f->f.getAnnotation(Id.class) != null).findFirst().orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(cls.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(cls.getDeclaredFields()).filter(f->f.getAnnotation(Id.class) == null).toList();
    }
}
