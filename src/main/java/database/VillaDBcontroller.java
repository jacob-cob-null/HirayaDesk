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

    //Mapping Villa table to tableView
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

    public static List<String> getAllTierIDs() throws SQLException {
        List<String> tierIDs = new ArrayList<>();
        Connection conn = MainDB.connect();
        String query = "SELECT tierID FROM Tier";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tierIDs.add(rs.getString("tierID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
        return tierIDs;
    }

    public static void createVilla(String tier) throws SQLException {
        Connection conn = MainDB.connect();
        String insertQuery = "INSERT INTO Villa (tierID, availability) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, tier);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int villaID = generatedKeys.getInt(1);

                Villa villa = new Villa(villaID, tier, true);
                rawVillaData.add(villa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
    }

    public static void updateVillaStatus(int villaID) throws SQLException {
        Connection conn = MainDB.connect();
        String updateQuery = "UPDATE Villa SET availability = 0 WHERE villaID = ?";  

        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setInt(1, villaID);
            int rowsAffected = pstmt.executeUpdate(); 
            if (rowsAffected > 0) {

                for (Villa villa : rawVillaData) {
                    if (villa.getVillaID() == villaID) {
                        villa.setAvailability(false);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
    }
}
