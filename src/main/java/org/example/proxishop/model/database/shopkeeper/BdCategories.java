package org.example.proxishop.model.database.shopkeeper;

import org.example.proxishop.model.entities.shopkeeper.ProductCategory;
import org.example.proxishop.model.entities.shopkeeper.ProductSubCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Manages the insertion, update, and deletion of Categories and sub-categories in the database.
 */
public class BdCategories {

    // Méthode pour vérifier l'existence de la table productcategory
    public boolean checkIfTableExists(String website_name, String tableName) throws SQLException {
        boolean tableExists = false;
        String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = '" + tableName + "'";
        System.out.println("Executing query: " + query); // Ajouter un log pour vérifier la requête

        try (Connection connection = BdConnection.establishConnection(website_name);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                tableExists = count > 0;
            }
        }

        return tableExists;
    }

    // Méthode pour récupérer toutes les catégories
    public List<ProductCategory> getAllCategories(String website_name) throws SQLException {
        List<ProductCategory> categoryNamesList = new ArrayList<>();
        String query = "SELECT * FROM productcategory";
        System.out.println("Executing query: " + query); // Ajouter un log pour vérifier la requête
        try (Connection connection = BdConnection.establishConnection(website_name);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String categoryName = resultSet.getString("categoryName");
                int categoryId = resultSet.getInt("id");
                categoryNamesList.add(new ProductCategory(categoryId, categoryName));
            }
            System.out.println("Category list displayed");
        }
        return categoryNamesList;
    }

    // Méthode pour récupérer toutes les sous-catégories
    public List<ProductSubCategory> getAllSubCategories(String website_name) throws SQLException {
        List<ProductSubCategory> subCategoryNamesList = new ArrayList<>();
        String query = "SELECT * FROM productsubcategory";
        System.out.println("Executing query: " + query);
        try (Connection connection = BdConnection.establishConnection(website_name);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String SubCategoryName = resultSet.getString("SubCategoryName");
                int catid = resultSet.getInt("id_category");
                subCategoryNamesList.add(new ProductSubCategory(id, SubCategoryName, catid));
            }
            System.out.println("Subcategory list displayed");
        }
        return subCategoryNamesList;
    }

    /**
     * Inserts category and subcategories into the database.
     *
     * @param categoryName     the name of the category
     * @param subcategories     an array list that contains the subcategories names
     * @param website_name      the name of the database
     * @throws SQLException     if a database access error occurs
     */
    public void insertCategoryAndSubCategory(String categoryName, String[] subcategories, String website_name) throws SQLException {
        try (Connection connection = BdConnection.establishConnection(website_name);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO productcategory (CategoryName) VALUES (?)")) {

            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
            System.out.println("Category " + categoryName + " created successfully.");
            int idrecup = getIdcategory(categoryName, website_name);
            System.out.println(idrecup);
            insertSubProductCategoryData(Arrays.asList(subcategories), idrecup, website_name);
        }
    }

    /**
     * Inserts a subcategory into the database.
     *
     * @param categoryId        the ID of the category
     * @param subCategoryName   the name of the subcategory
     * @param website_name      the name of the database
     * @throws SQLException if a database access error occurs
     */
    public void insertSubCategory(int categoryId, String subCategoryName, String website_name) throws SQLException {
        try (Connection connection = BdConnection.establishConnection(website_name);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO productsubcategory (SubCategoryName, id_category) VALUES (?, ?)")) {

            preparedStatement.setString(1, subCategoryName);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.executeUpdate();
            System.out.println("SubCategory " + subCategoryName + " created successfully.");
        }
    }

    /**
     * Retrieves the ID of a category.
     *
     * @param categoryName the name of the category
     * @param website_name      the name of the database
     * @return the ID of the category
     * @throws SQLException if a database access error occurs
     */
    private int getIdcategory(String categoryName, String website_name) throws SQLException {
        try (Connection connection = BdConnection.establishConnection(website_name);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id FROM productcategory WHERE CategoryName = ?")) {
            preparedStatement.setString(1, categoryName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("Category not found: " + categoryName);
                }
            }
        }
    }

    /**
     * Inserts subcategories into the database.
     *
     * @param productSubCategory a list of subcategory names
     * @param id_category        the ID of the category
     * @param website_name            the name of the database
     * @throws SQLException if a database access error occurs
     */
    private void insertSubProductCategoryData(List<String> productSubCategory, int id_category, String website_name) throws SQLException {
        for (String SubCategory : productSubCategory) {
            if (!SubCategory.isEmpty()) {
                try (Connection connection = BdConnection.establishConnection(website_name);
                     PreparedStatement preparedStatement = connection.prepareStatement(
                             "INSERT INTO productsubcategory (SubCategoryName, id_category) VALUES (?, ?)")) {

                    preparedStatement.setString(1, SubCategory);
                    preparedStatement.setInt(2, id_category);
                    preparedStatement.executeUpdate();

                    System.out.println("SubCategory " + SubCategory + " created successfully.");
                }
            }
        }
    }
}