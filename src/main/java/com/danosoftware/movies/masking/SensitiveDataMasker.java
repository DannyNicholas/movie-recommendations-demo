package com.danosoftware.movies.masking;

import java.lang.reflect.Field;

public class SensitiveDataMasker {

    public static String maskSensitiveFields(Object object) {
        if (object == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(object.getClass().getSimpleName()).append("{");

        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            try {
                Object value = field.get(object);
                if (field.isAnnotationPresent(Sensitive.class)) {
                    sb.append(field.getName()).append("=").append(maskValue(value));
                } else {
                    sb.append(field.getName()).append("=").append(value);
                }
            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append("=ERROR");
            }

            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private static String maskValue(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString().replaceAll(".", "*");
    }
}