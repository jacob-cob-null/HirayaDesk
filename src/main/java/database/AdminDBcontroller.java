/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jacob
 */
public class AdminDBcontroller {



    //AUTHENTICATE
   public static String authenticate(int userID, String password) {
    boolean status = false;
    Connection conn = MainDB.connect();
    String query = "SELECT * FROM Admin WHERE adminID = ? AND adminPW = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, userID);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        status = rs.next(); // True if there's a match
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        MainDB.closeConnection(conn);
    }
    return "Authentication status: " + status;
}

}
