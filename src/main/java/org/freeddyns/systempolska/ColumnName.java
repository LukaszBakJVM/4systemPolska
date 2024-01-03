package org.freeddyns.systempolska;

import jakarta.persistence.Column;

import org.freeddyns.systempolska.Model.Users;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ColumnName {


    public Set<String> getAllColumnNames(Class<Users> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    return columnAnnotation.name();
                })
                .collect(Collectors.toSet());
    }

}
