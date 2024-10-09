package org.example.proxishop.model.database.shopkeeper;

import org.example.proxishop.model.entities.shopkeeper.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the insertion, update, and deletion of Products in the database.
 */
public class BdProducts {

    /**
     * Retrieves all products from the database.
     *
     * @param bddname the name of the database
     * @return a list of Product objects
     * @throws SQLException if a database access error occurs
     */
    public List<Product> getAllProducts(String bddname) throws SQLException {
        List<Product> productNamesList = new ArrayList<>();
        String query = "SELECT * FROM product";
        System.out.println("Executing query: " + query); // Ajouter un log pour vérifier la requête
        try (Connection connection = BdConnection.establishConnection(bddname);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String productName = resultSet.getString("productName");
                int subcatid = resultSet.getInt("id_subCategory");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int stockProd = resultSet.getInt("stock");
                String imageProd = resultSet.getString("image");
                productNamesList.add(new Product(id, productName, description, stockProd, imageProd, price, subcatid));
            }
            System.out.println("Product list displayed");
        }
        return productNamesList;
    }

    /**
     * Inserts a new product into the database.
     *
     * @param subCategoryid the ID of the subcategory
     * @param website_name       the name of the database
     * @param productName   the name of the product
     * @param description   the description of the product
     * @param price         the price of the product
     * @param stock         the stock of the product
     * @param image         the image of the product
     * @throws SQLException if a database access error occurs
     */
    public void insertNewProduct(int subCategoryid, String website_name, String productName, String description, double price, int stock, String image) throws SQLException {
        String query = "INSERT INTO " + website_name + ".product (productName, description, stock, image, price, id_subCategory) VALUES (?, ?, ?, ?, ?, ?)";
        System.out.println("Executing query: " + query); // Ajouter un log pour vérifier la requête
        try (Connection connection = BdConnection.establishConnection(website_name);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, stock);
            preparedStatement.setString(4, image);
            preparedStatement.setDouble(5, price);
            preparedStatement.setInt(6, subCategoryid);
            preparedStatement.executeUpdate();

            System.out.println("Product " + productName + " created successfully.");
        }
    }

    /**
     * Updates product data in the database.
     *
     * @param productid      the ID of the product
     * @param subCategoryid  the ID of the subcategory
     * @param website_name        the name of the database
     * @param productName    the name of the product
     * @param description    the description of the product
     * @param price          the price of the product
     * @param stock          the stock of the product
     * @param image          the image of the product
     * @throws SQLException if a database access error occurs
     */
    public void updateProduct(int productid, int subCategoryid, String website_name, String productName, String description, double price, int stock, String image) throws SQLException {
        String query = "UPDATE " + website_name + ".product SET productName = ?, description = ?, stock = ?, image = ?, price = ?, id_subCategory = ? WHERE id = ?";
        System.out.println("Executing query: " + query); // Ajouter un log pour vérifier la requête
        try (Connection connection = BdConnection.establishConnection(website_name);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, stock);
            preparedStatement.setString(4, image);
            preparedStatement.setDouble(5, price);
            preparedStatement.setInt(6, subCategoryid);
            preparedStatement.setInt(7, productid);
            int updated = preparedStatement.executeUpdate();

            if (updated > 0) {
                System.out.println("Product : " + productName + " update successfully.");
            } else {
                System.out.println(" !! Product : " + productName + " Update failed.");
            }
        }
    }

    /**
     * Deletes a product from the database.
     *
     * @param productid   the ID of the product
     * @param website_name     the name of the database
     * @param productName the name of the product
     * @throws SQLException if a database access error occurs
     */
    public void deleteProduct(int productid, String website_name, String productName) throws SQLException {
        String query = "DELETE FROM " + website_name + ".product WHERE id = ?";
        System.out.println("Executing query: " + query); // Ajouter un log pour vérifier la requête
        try (Connection connection = BdConnection.establishConnection(website_name);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productid);
            int updated = preparedStatement.executeUpdate();

            if (updated > 0) {
                System.out.println("Product : " + productName + " delete successfully.");
            } else {
                System.out.println(" !! Product : " + productName + " Delete failed.");
            }
        }
    }
}