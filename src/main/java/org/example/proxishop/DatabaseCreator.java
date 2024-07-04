package org.example.proxishop;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DatabaseCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public void createDatabaseAndTables(String databaseName, List<Class<?>> classes) {
        Connection connection = null;
        Statement statement = null;

        try {
            // Établir une connexion à la base de données principale
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();

            // Créer la base de données
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database "+ databaseName + " created successfully.");

            // Utiliser la nouvelle base de données
            String useDatabaseSQL = "USE " + databaseName;
            statement.executeUpdate(useDatabaseSQL);

            // Créer les tables
            List<String> createTableSQLs = TablesCreator.generateCreateTableSQL(classes);
            for (String createTableSQL : createTableSQLs) {
                statement.executeUpdate(createTableSQL);
            }
            System.out.println("All Tables created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
