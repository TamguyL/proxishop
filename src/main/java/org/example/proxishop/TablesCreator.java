package org.example.proxishop;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TablesCreator {
    public static List<String> generateCreateTableSQL(List<Class<?>> classes) {
        List<String> sqlStatements = new ArrayList<>();
        for (Class<?> clazz : classes) {
            StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE IF NOT EXISTS ").append(clazz.getSimpleName()).append(" (");

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                sql.append(field.getName()).append(" ");

                if (field.getType().equals(Double.class)) {
                    sql.append("DOUBLE");
                } else if (field.getType().equals(String.class)) {
                    sql.append("VARCHAR(255)");
                } else {
                    throw new IllegalArgumentException("Type non supporté: " + field.getType());
                }

                if (field.getName().equals("id")) {
                    sql.append(" PRIMARY KEY AUTO_INCREMENT");
                }

                sql.append(", ");
            }

            sql.setLength(sql.length() - 2);
            sql.append(")");

            sqlStatements.add(sql.toString());
        }

        return sqlStatements;
    }
}
