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
                int categoryId = resultSet.getInt("id");
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
            int idrecup = getIdcategory(categoryName, bddname);
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
    private int getIdcategory(String categoryName, String bddname) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id FROM productcategory WHERE CategoryName = (?)")) {
            statement.executeUpdate("USE " + bddname);
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
     * @param bddname            the name of the database
     * @throws SQLException if a database access error occurs
     */
    private void insertSubProductCategoryData(List<String> productSubCategory, int id_category, String bddname) throws SQLException {
        for (String SubCategory : productSubCategory) {
            try (Connection connection = BdConnection.establishConnection();
                 Statement statement = connection.createStatement();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO productsubcategory (SubCategoryName, id_category) VALUES (?, ?)")) {

                statement.executeUpdate("USE " + bddname);
                preparedStatement.setString(1, SubCategory);
                preparedStatement.setInt(2, id_category);
                preparedStatement.executeUpdate();

                System.out.println("SubCategory " + SubCategory + " created successfully.");
            }
        }
    }

    /**
     * Updates category data in the database.
     *
     * @param bddname       Le nom de la base de données.
     * @param categoryName   Le nom de le category.
     * @param id_category    L'ID du produit à mettre à jour.
     * @throws SQLException if a database access error occurs
     */
    public void  updateCategory(int id_category, String bddname, String categoryName) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE productcategory SET CategoryName = ? WHERE id = ?")) {

            statement.executeUpdate("USE " + bddname);

            preparedStatement.setString(1, categoryName);
            preparedStatement.setInt(2, id_category);
            int updated = preparedStatement.executeUpdate();

            if (updated > 0) {
                System.out.println("Category : " + categoryName + " update successfully.");
            } else {
                System.out.println(" !! Category : " + categoryName + " Update failed.");
            }
        }
    }

    /**
     * Inserts category and subcategories into the database.
     *
     * @param categoryName     the name of the category
     * @param bddname          the name of the database
     * @throws SQLException if a database access error occurs
     */
    public void insertCategory(String categoryName, String bddname) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO productcategory (CategoryName) VALUES (?)")) {

            statement.executeUpdate("USE " + bddname);
            preparedStatement.setString(1, categoryName);
            int updated = preparedStatement.executeUpdate();
            if (updated > 0) {
                System.out.println("Category : " + categoryName + " created successfully.");
            } else {
                System.out.println(" !! Category : " + categoryName + " created failed.");
            }
        }
    }

    /**
     * Deletes a product from the database.
     *
     * @param id_category  the ID of the product
     * @param bddname     the name of the database
     * @throws SQLException if a database access error occurs
     */
    public void  deleteCategory(int id_category, String bddname) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement deleteCategoryStatement = connection.prepareStatement(
                     "DELETE FROM productcategory WHERE id = ?");
             PreparedStatement deleteSubCategoriesStatement = connection.prepareStatement(
                     "DELETE FROM productsubcategory WHERE id_category = ?")) {

            statement.executeUpdate("USE " + bddname);

            // Supprimer la catégorie
            deleteCategoryStatement.setInt(1, id_category);
            int deletedCategories = deleteCategoryStatement.executeUpdate();

            if (deletedCategories > 0) {
                System.out.println("Category with id " + id_category + " deleted successfully.");
            } else {
                System.out.println("No category found with id " + id_category + ".");
            }

            // Supprimer les sous-catégories associées
            deleteSubCategoriesStatement.setInt(1, id_category);
            int deletedSubCategories = deleteSubCategoriesStatement.executeUpdate();

            if (deletedSubCategories > 0) {
                System.out.println("Subcategories with id_category " + id_category + " deleted successfully.");
            } else {
                System.out.println("No subcategories found with id_category " + id_category + ".");
            }
        }
    }

    /**
     * Inserts category and subcategories into the database.
     *
     * @param id_category       Id de la categorie
     * @param subCategoryName     the name of the category
     * @param bddname          the name of the database
     * @throws SQLException if a database access error occurs
     */
    public void insertSubCategory(int id_category, String subCategoryName, String bddname) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO productsubcategory (SubCategoryName, id_category) VALUES (?, ?)")) {

            statement.executeUpdate("USE " + bddname);
            preparedStatement.setString(1, subCategoryName);
            preparedStatement.setInt(2, id_category);
            int updated = preparedStatement.executeUpdate();
            if (updated > 0) {
                System.out.println("SubCategory : " + subCategoryName + " created successfully.");
            } else {
                System.out.println(" !! SubCategory : " + subCategoryName + " created failed.");
            }
        }
    }

    /**
     * Updates category data in the database.
     *
     * @param bddname       Le nom de la base de données.
     * @param SubCategoryName   Le nom de le category.
     * @param id_subCategory    L'ID de le sous category à mettre à jour.
     * @throws SQLException if a database access error occurs
     */
    public void  updateSubCategory(int id_subCategory, String bddname, String SubCategoryName) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE productsubcategory SET SubCategoryName = ? WHERE id = ?")) {

            statement.executeUpdate("USE " + bddname);

            preparedStatement.setString(1, SubCategoryName);
            preparedStatement.setInt(2, id_subCategory);
            int updated = preparedStatement.executeUpdate();

            if (updated > 0) {
                System.out.println("Category : " + SubCategoryName + " update successfully.");
            } else {
                System.out.println(" !! Category : " + SubCategoryName + " Update failed.");
            }
        }
    }

    /**
     * Deletes a product from the database.
     *
     * @param bddname       Le nom de la base de données.
     * @param id_subCategory    L'ID de le sous category à mettre à jour.
     * @throws SQLException if a database access error occurs
     */
    public void  deleteSubCategory(int id_subCategory, String bddname) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement deleteSubCategoriesStatement = connection.prepareStatement(
                     "DELETE FROM productsubcategory WHERE id = ?")) {

            statement.executeUpdate("USE " + bddname);

            deleteSubCategoriesStatement.setInt(1, id_subCategory);
            int deletedSubCategories = deleteSubCategoriesStatement.executeUpdate();

            if (deletedSubCategories > 0) {
                System.out.println("Subcategories with id_category " + id_subCategory + " deleted successfully.");
            } else {
                System.out.println("No subcategories found with id_category " + id_subCategory + ".");
            }
        }
    }

}
