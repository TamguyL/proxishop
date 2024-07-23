package org.example.proxishop.model.database;

import org.example.proxishop.model.entities.shopkeeper.Shopkeeper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    // Fonction maitre pour la création de la base de données et des differents tables
    public void createDatabaseAndTables(String databaseName, List<Class<?>> classes, Shopkeeper shopkeeper) {
        try {
            validateParameters(databaseName, classes);
            Connection connection = establishConnection();
            createDatabaseAndTables(connection, databaseName, classes);
            insertShopkeeperData(connection, shopkeeper);
        } catch (SQLException e) {
            System.err.println("SQL error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }

    // Pour éviter l'injection SQL
    private void validateParameters(String databaseName, List<Class<?>> classes) {
        if (databaseName == null || databaseName.trim().isEmpty() || classes == null || classes.isEmpty()) {
            throw new IllegalArgumentException("Database name and classes must not be null or empty.");
        }
        if (!databaseName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid database name.");
        }
    }

    // Pour établir la connection à la base de données
    private Connection establishConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Création de la base de données et appel de la fonction pour créé les tables
    private void createDatabaseAndTables(Connection connection, String databaseName, List<Class<?>> classes) throws SQLException {
        Statement statement = connection.createStatement();

        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        statement.executeUpdate(createDatabaseSQL);
        System.out.println("Database " + databaseName + " created successfully.");

        String useDatabaseSQL = "USE " + databaseName;
        statement.executeUpdate(useDatabaseSQL);

        List<String> createTableSQLs = generateCreateTableSQL(classes);
        for (String createTableSQL : createTableSQLs) {
            statement.executeUpdate(createTableSQL);
        }
        System.out.println("All Tables created successfully.");
    }

    // Création des tables
    private static List<String> generateCreateTableSQL(List<Class<?>> classes) {
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

    // Ajout des données du Shopkeeper dans la bdd de son site
    private void insertShopkeeperData(Connection connection, Shopkeeper shopkeeper) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO shopkeeper (siret, firstName, lastName, email, adress, profilePicture) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setDouble(1, shopkeeper.getSiret()); // siret
        preparedStatement.setString(2, shopkeeper.getFirstName()); // firstName
        preparedStatement.setString(3, shopkeeper.getLastName()); // lastName
        preparedStatement.setString(4, shopkeeper.getEmail()); // email
        preparedStatement.setString(5, shopkeeper.getAdress()); // adress
        preparedStatement.setString(6, shopkeeper.getProfilePicture()); // profilePicture
        preparedStatement.executeUpdate();
        System.out.println("Shopkeeper Profils created successfully.");
    }

    // Ajout des Categories produits dans la Bdd Shopkeeper
    public void insertProductCategoryData(Double id, String categoryName, String bddname) throws SQLException {
        try (Connection connection = establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO productcategory (id, CategoryName) VALUES (?, ?)")) {

            statement.executeUpdate("USE " + bddname);

            preparedStatement.setDouble(1, id);
            preparedStatement.setString(2, categoryName);
            preparedStatement.executeUpdate();

            System.out.println("Category " + categoryName + " created successfully.");
        }
    }

    // Ajout des Produits dans la Bdd Shopkeeper
    public void insertProductData(Double id, String productName, String description, Double stock, String image, Double price, Double id_subCategory, String bddname) throws SQLException {
        try (Connection connection = establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO product (id, productName, description, stock, image, price, id_subCategory) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            statement.executeUpdate("USE " + bddname);

            preparedStatement.setDouble(1, id);
            preparedStatement.setString(2, productName);
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, stock);
            preparedStatement.setString(5, image);
            preparedStatement.setDouble(6, price);
            preparedStatement.setDouble(7, id_subCategory);
            preparedStatement.executeUpdate();

            System.out.println("Product " + productName + " created successfully.");
        }
    }

    public void insertSocialMedia(String bddname, String x, String instagram, String facebook, String tiktok, String whatsapp) throws SQLException {
        try (Connection connection = establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO socialmedia (x, insta, fb, tiktok, whatsapp) VALUES (?, ?, ?, ?, ?)")) {

            statement.executeUpdate("USE " + bddname);

            preparedStatement.setString(1, x);
            preparedStatement.setString(2, instagram);
            preparedStatement.setString(3, facebook);
            preparedStatement.setString(4, tiktok);
            preparedStatement.setString(5, whatsapp);
            preparedStatement.executeUpdate();

            System.out.println("Social Media created successfully.");
        }
    }

    public List<String> getAllCategories(String bddname) throws SQLException {
        List<String>categoryNamesList = new ArrayList<>();
        try (Connection connection = establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
        "SELECT categoryName FROM productcategory")){

            statement.execute("USE " + bddname);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                String categoryName = resultSet.getString("categoryName");
                categoryNamesList.add(categoryName);
            }
            System.out.println("category list displayed");

        }
        return categoryNamesList;
    }



}

