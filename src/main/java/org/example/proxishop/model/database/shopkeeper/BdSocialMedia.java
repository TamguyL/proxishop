package org.example.proxishop.model.database.shopkeeper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Manages the insertion, update, and deletion of Social Media in the database.
 */
public class BdSocialMedia {

    /**
     * Inserts social media data into the database.
     *
     * @param bddname   the name of the database
     * @param x         the X link
     * @param instagram the Instagram link
     * @param facebook  the Facebook link
     * @param tiktok    the TikTok link
     * @param whatsapp  the WhatsApp link
     * @throws SQLException if a database access error occurs
     */
    public void insertSocialMedia(String bddname, String x, String instagram, String facebook, String tiktok, String whatsapp) throws SQLException {
        try (Connection connection = BdConnection.establishConnection(bddname);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO socialmedia (x, insta, fb, tiktok, whatsapp) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, x);
            preparedStatement.setString(2, instagram);
            preparedStatement.setString(3, facebook);
            preparedStatement.setString(4, tiktok);
            preparedStatement.setString(5, whatsapp);
            preparedStatement.executeUpdate();

            System.out.println("Social Media created successfully.");
        }
    }
}