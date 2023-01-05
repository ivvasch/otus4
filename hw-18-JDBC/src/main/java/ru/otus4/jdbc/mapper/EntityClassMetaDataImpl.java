package ru.otus4.jdbc.mapper;

import ru.otus4.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final T clazz;
    private final Constructor<?>[] declaredConstructors;
    private final Field[] declaredFields;

    public EntityClassMetaDataImpl(T clazz) {
        this.clazz = clazz;
        this.declaredConstructors = clazz.getClass().getDeclaredConstructors();
        this.declaredFields =  clazz.getClass().getDeclaredFields();
    }

    @Override
    public String getName() {
        return clazz.getClass().getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        return (Constructor<T>) declaredConstructors[0];

    }

    @Override
    public Field getIdField() {
        Optional<Field> idField = getAllFields().stream().filter(f -> f.isAnnotationPresent(Id.class)).findFirst();
        return idField.orElse(null);

    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(declaredFields).collect(Collectors.toList());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream().filter(f -> !f.isAnnotationPresent(Id.class)).collect(Collectors.toList());
    }
}
