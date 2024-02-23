package com.example.parking.fake;

import java.lang.reflect.Field;

public interface BasicRepository<T, ID> {

    default void setId(T entity, ID id) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
