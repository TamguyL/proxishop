package org.example.proxishop;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public void createDatabaseAndTables(String databaseName) {
        Connection connection = null;
        Statement statement = null;

        try {
            // Établir une connexion à la base de données principale
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();

            // Créer la base de données
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database created successfully...");

            // Utiliser la nouvelle base de données
            String useDatabaseSQL = "USE " + databaseName;
            statement.executeUpdate(useDatabaseSQL);

            // Créer les tables
            String createTableSQL = "CREATE TABLE IF NOT EXISTS example_table (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Table created successfully...");

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

    public static void main(String[] args) {
        DatabaseCreator creator = new DatabaseCreator();
        creator.createDatabaseAndTables("new_database_name");
    }
}
