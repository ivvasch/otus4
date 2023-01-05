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

    public EntityClassMetaDataImpl(T clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        Optional<Field> name = getAllFields().stream().filter(f -> f.getName().equals("name")).findFirst();
        return name.map(Field::getName).orElse(null);
    }

    @Override
    public Constructor<T> getConstructor() {
        Constructor<?>[] declaredConstructors = clazz.getClass().getDeclaredConstructors();
        return (Constructor<T>) declaredConstructors[0];
    }

    @Override
    public Field getIdField() {
        Optional<Field> idField = getAllFields().stream().filter(f -> f.isAnnotationPresent(Id.class)).findFirst();
        return idField.orElse(null);
    }

    @Override
    public List<Field> getAllFields() {
        Field[] declaredFields =  clazz.getClass().getDeclaredFields();
        return Arrays.stream(declaredFields).collect(Collectors.toList());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream().filter(f -> !f.isAnnotationPresent(Id.class)).collect(Collectors.toList());
    }
}
