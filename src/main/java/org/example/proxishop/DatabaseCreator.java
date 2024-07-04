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

        // Lancer une exception si l'un ou plusieurs des paramètres sont null ou vides
        if (databaseName == null || databaseName.trim().isEmpty() || classes == null || classes.isEmpty()) {
            throw new IllegalArgumentException("Database name and classes must not be null or empty.");
        }

        try {
            // Établir une connexion à la base de données principale
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            //Verification pour éviter l'injection SQL
            if (!databaseName.matches("^[a-zA-Z0-9_]+$")) {
                throw new IllegalArgumentException("Invalid database name.");
            }

            // Créer la base de données
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database "+ databaseName + " created successfully.");

            // Utiliser la nouvelle base de données
            String useDatabaseSQL = "USE " + databaseName;
            statement.executeUpdate(useDatabaseSQL);

            // Créer les tables en appelant la fonction de TablesCreator
            List<String> createTableSQLs = TablesCreator.generateCreateTableSQL(classes);
            for (String createTableSQL : createTableSQLs) {
                statement.executeUpdate(createTableSQL);
            }
            System.out.println("All Tables created successfully.");

        } catch (SQLException e) {
            // Journalisation sécurisée sans exposer de données sensibles
            System.err.println("SQL error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Gérer les exceptions d'arguments invalides
            System.err.println("Invalid input: " + e.getMessage());
        }
    }
}
