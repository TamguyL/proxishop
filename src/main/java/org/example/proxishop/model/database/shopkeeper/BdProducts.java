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
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM product")) {

            statement.execute("USE " + bddname);
            ResultSet resultSet = preparedStatement.executeQuery();

            for (int i = 1; resultSet.next(); i++) {
                double id = resultSet.getDouble("id");
                String productName = resultSet.getString("productName");
                double subcatid = resultSet.getDouble("id_subCategory");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                double stockProd = resultSet.getDouble("stock");
                String imageProd = resultSet.getString("image");
                productNamesList.add(new Product(id, productName,description,stockProd,imageProd,price,subcatid));
            }
            System.out.println("Product list displayed");

        }
        return productNamesList;
    }

    /**
     * Inserts a new product into the database.
     *
     * @param subCategoryid the ID of the subcategory
     * @param bddname       the name of the database
     * @param productName   the name of the product
     * @param description   the description of the product
     * @param price         the price of the product
     * @param stock         the stock of the product
     * @param image         the image of the product
     * @throws SQLException if a database access error occurs
     */
    public void  insertNewProduct(double subCategoryid, String bddname, String productName, String description, double price, double stock, String image) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO product (productName, description, stock, image, price, id_subCategory) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.executeUpdate("USE " + bddname);

            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, description);
            preparedStatement.setDouble(3, stock);
            preparedStatement.setString(4, image);
            preparedStatement.setDouble(5, price);
            preparedStatement.setDouble(6, subCategoryid);
            preparedStatement.executeUpdate();

            System.out.println("Product " + productName + " created successfully.");
        }
    }

    /**
     * Updates product data in the database.
     *
     * @param productid      the ID of the product
     * @param subCategoryid  the ID of the subcategory
     * @param bddname        the name of the database
     * @param productName    the name of the product
     * @param description    the description of the product
     * @param price          the price of the product
     * @param stock          the stock of the product
     * @param image          the image of the product
     * @throws SQLException if a database access error occurs
     */
    public void  updateProduct(int productid, double subCategoryid, String bddname, String productName, String description, double price, double stock, String image) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE product SET productName = ?, description = ?, stock = ?, image = ?, price = ?, id_subCategory = ? WHERE id = ?")) {

            statement.executeUpdate("USE " + bddname);

            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, description);
            preparedStatement.setDouble(3, stock);
            preparedStatement.setString(4, image);
            preparedStatement.setDouble(5, price);
            preparedStatement.setDouble(6, subCategoryid);
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
     * @param bddname     the name of the database
     * @param productName the name of the product
     * @throws SQLException if a database access error occurs
     */
    public void  deleteProduct(int productid, String bddname, String productName) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM product WHERE id = ?")) {

            statement.executeUpdate("USE " + bddname);

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
