package org.example.proxishop.model.database.costumer;

import org.example.proxishop.model.database.shopkeeper.BdConnection;
import org.example.proxishop.model.entities.customer.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BdOrder {

    /**
     * Retrieves all orders from the database.
     *
     * @param bddname the name of the database
     * @return a list of Orders objects
     * @throws SQLException if a database access error occurs
     */
    public List<Orders> getOrderlist(String bddname) throws SQLException {
        List<Orders> orderList = new ArrayList<>();
        String query = "SELECT id, tags, finalPrice, orderNumber, state FROM " + bddname + ".orders";
        System.out.println("Executing query: " + query); // Ajouter un log pour vérifier la requête
        try (Connection connection = BdConnection.establishConnection(bddname);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Double id = resultSet.getDouble("id");
                String tag = resultSet.getString("tags");
                double fprice = resultSet.getDouble("finalPrice");
                String onumber = resultSet.getString("orderNumber");
                String ostate = resultSet.getString("state");
                orderList.add(new Orders(id, tag, fprice, onumber, ostate));
            }
            return orderList;
        }
    }

    /**
     * Updates order state in the database.
     *
     * @param id     the ID of the order
     * @param state  the order's state
     * @param bddname the name of the database
     * @throws SQLException if a database access error occurs
     */
    public void updateOrderState(Double id, String state, String bddname) throws SQLException {
        String query = "UPDATE " + bddname + ".orders SET state = ? WHERE id = ?";
        System.out.println("Executing query: " + query); // Ajouter un log pour vérifier la requête
        try (Connection connection = BdConnection.establishConnection(bddname);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, state);
            preparedStatement.setDouble(2, id);
            int updated = preparedStatement.executeUpdate();

            if (updated > 0) {
                System.out.println("State of Order n° " + id + " updated successfully.");
            } else {
                System.out.println("Update of Order n° " + id + " state failed!");
            }
        }
    }
}