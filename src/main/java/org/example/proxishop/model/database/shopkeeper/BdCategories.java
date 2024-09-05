package org.example.proxishop.model.database.shopkeeper;

import org.example.proxishop.model.entities.shopkeeper.ProductCategory;
import org.example.proxishop.model.entities.shopkeeper.ProductSubCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Manages the insertion, update, and deletion of Categories ans sub-categories in the database.
 */
public class BdCategories {

    /**
     * Retrieves all categories from the database.
     *
     * @param bddname the name of the database
     * @return a list of ProductCategory objects
     * @throws SQLException if a database access error occurs
     */
    public List<ProductCategory> getAllCategories(String bddname) throws SQLException {
        List<ProductCategory> categoryNamesList = new ArrayList<>();
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM productcategory")) {

            statement.execute("USE " + bddname);
            ResultSet resultSet = preparedStatement.executeQuery();

            for (int i = 1; resultSet.next(); i++) {
                String categoryName = resultSet.getString("categoryName");
                double categoryId = resultSet.getDouble("id");
                categoryNamesList.add(new ProductCategory(categoryId, categoryName));
            }
            System.out.println("category list displayed");
        }
        return categoryNamesList;
    }

    /**
     * Retrieves all subcategories from the database.
     *
     * @param bddname the name of the database
     * @return a list of ProductSubCategory objects
     * @throws SQLException if a database access error occurs
     */
    public List<ProductSubCategory> getAllSubCategories(String bddname) throws SQLException {
        List<ProductSubCategory> subCategoryNamesList = new ArrayList<>();
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM productsubcategory")) {

            statement.execute("USE " + bddname);
            ResultSet resultSet = preparedStatement.executeQuery();

            for (int i = 1; resultSet.next(); i++) {
                double id = resultSet.getDouble("id");
                String SubCategoryName = resultSet.getString("SubCategoryName");
                double catid = resultSet.getDouble("id_category");
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
     * @param subcategoryName1 the name of the first subcategory
     * @param subcategoryName2 the name of the second subcategory
     * @param subcategoryName3 the name of the third subcategory
     * @param subcategoryName4 the name of the fourth subcategory
     * @param subcategoryName5 the name of the fifth subcategory
     * @param bddname          the name of the database
     * @throws SQLException if a database access error occurs
     */
    public void insertCategoryAndSubCategory(String categoryName, String subcategoryName1, String subcategoryName2, String subcategoryName3, String subcategoryName4, String subcategoryName5, String bddname) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO productcategory (CategoryName) VALUES (?)")) {

            statement.executeUpdate("USE " + bddname);
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
            System.out.println("Category " + categoryName + " created successfully.");
            double idrecup = getIdcategory(categoryName, bddname);
            System.out.println(idrecup);
            List<String> productSubCategory = new ArrayList<>();
            productSubCategory.add(subcategoryName1);
            Stream.of(subcategoryName2, subcategoryName3, subcategoryName4, subcategoryName5)
                    .filter(name -> !name.isEmpty())
                    .forEach(productSubCategory::add);
            insertSubProductCategoryData(productSubCategory, idrecup, bddname);
        }
    }

    /**
     * Retrieves the ID of a category.
     *
     * @param categoryName the name of the category
     * @param bddname      the name of the database
     * @return the ID of the category
     * @throws SQLException if a database access error occurs
     */
    private double getIdcategory(String categoryName, String bddname) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id FROM productcategory WHERE CategoryName = (?)")) {
            statement.executeUpdate("USE " + bddname);
            preparedStatement.setString(1, categoryName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("id");
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
     * @param bddname            the name of the database
     * @throws SQLException if a database access error occurs
     */
    private void insertSubProductCategoryData(List<String> productSubCategory, Double id_category, String bddname) throws SQLException {
        for (String SubCategory : productSubCategory) {
            try (Connection connection = BdConnection.establishConnection();
                 Statement statement = connection.createStatement();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO productsubcategory (SubCategoryName, id_category) VALUES (?, ?)")) {

                statement.executeUpdate("USE " + bddname);
                preparedStatement.setString(1, SubCategory);
                preparedStatement.setDouble(2, id_category);
                preparedStatement.executeUpdate();

                System.out.println("SubCategory " + SubCategory + " created successfully.");
            }
        }
    }


}
