package org.example.proxishop.model.database.costumer;

import org.example.proxishop.model.database.shopkeeper.BdConnection;
import org.example.proxishop.model.entities.customer.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BdOrder {


    public List<Orders> getOrderlist(String bddname) throws SQLException {
        List<Orders> orderList= new ArrayList<>();

        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, tags, finalPrice, orderNumber, state FROM orders")){
            statement.executeUpdate("USE " + bddname);
            ResultSet resultSet = preparedStatement.executeQuery();
            for (int i = 1; resultSet.next(); i++) {
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
//
//    public void updateOrderState(Double id, String state) throws SQLException {
//        String sql = "UPDATE orders SET state = ? WHERE id = ?";
//        try (Connection connection = BdConnection.establishConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, state);
//            preparedStatement.setDouble(2, id);
//            preparedStatement.executeUpdate();
//        }
//    }

    /**
     * Updates order state in the database.
     *
     * @param id     the ID of the order
     * @param state  the order's state
     * @param bddname        the name of the database
     * @throws SQLException if a database access error occurs
     */

    public void  updateOrderState(Double id, String state, String bddname) throws SQLException {
        try (Connection connection = BdConnection.establishConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE orders SET state = ? WHERE id = ?")) {

            statement.executeUpdate("USE " + bddname);

            preparedStatement.setString(1, state);
            preparedStatement.setDouble(2, id);
            int updated = preparedStatement.executeUpdate();

            if (updated > 0) {
                System.out.println("State of Order n° " + id + " updated successfully.");
            } else {
                System.out.println(" update of Order n° " + id + " state failed!");
            }
        }
    }


}
