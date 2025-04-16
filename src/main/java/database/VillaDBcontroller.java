/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.objects.Villa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jacob
 */
public class VillaDBcontroller {

    public static List<Villa> rawVillaData = new ArrayList<>();

    public static void mapVilla() throws SQLException {
        rawVillaData.clear();

        Connection conn = MainDB.connect();
        String query = "SELECT * FROM Villa";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int villaID = rs.getInt("villaID");
                String tierID = rs.getString("tierID");
                boolean availability = (rs.getInt("availability") == 1);
                rawVillaData.add(new Villa(villaID, tierID, availability));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
    }

    public static void deleteVilla(int id) throws SQLException {
        Connection conn = MainDB.connect();
        String query = "DELETE FROM Villa WHERE villaID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            rawVillaData.removeIf(Villa -> Villa.getVillaID() == id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
    }
    //get villaID
    public static List<Integer> getAllVillaIDs() throws SQLException {
        List<Integer> villaIDs = new ArrayList<>();
        Connection conn = MainDB.connect();
        String query = "SELECT villaID FROM Villa";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                villaIDs.add(rs.getInt("villaID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }

        return villaIDs;
    }
}
