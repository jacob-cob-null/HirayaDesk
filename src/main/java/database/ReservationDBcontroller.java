/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import static database.VillaDBcontroller.rawVillaData;
import database.objects.Reservation;
import database.objects.Villa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jacob
 */
public class ReservationDBcontroller {

    public static List<Reservation> rawReservationData = new ArrayList<>();

    //Mapping Reservation table to tableView
    public static void mapReservation() throws SQLException {
        rawReservationData.clear();

        Connection conn = MainDB.connect();
        String query = "SELECT * FROM Reservation ORDER BY endDate DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int reservationID = rs.getInt("reservationID");
                String custName = rs.getString("custName");
                String custContactNumber = rs.getString("custContactNumber");
                int villaID = rs.getInt("villaID");
                int duration = rs.getInt("duration");
                String startDateStr = rs.getString("startDate");
                String endDateStr = rs.getString("endDate");
                int price = rs.getInt("price");

                //convert dates to local date
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                rawReservationData.add(new Reservation(reservationID, custName, custContactNumber,
                        villaID, duration, startDate, endDate, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
    }

    //CREATE Reservation Record
    public static void createWithJoin(String name, String contact, int villaID, int duration, String startDateStr, String endDateStr) {
        Connection conn = MainDB.connect();
        String joinQuery = "SELECT t.price FROM Villa v JOIN Tier t ON v.tierID = t.tierID WHERE v.villaID = ?";
        String insertQuery = "INSERT INTO Reservation (custName, custContactNumber, villaID, duration, startDate, endDate, price) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                PreparedStatement joinStmt = conn.prepareStatement(joinQuery); PreparedStatement insertStmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            joinStmt.setInt(1, villaID);
            ResultSet rs = joinStmt.executeQuery();

            if (rs.next()) {
                int pricePerDay = rs.getInt("price");
                int totalPrice = pricePerDay * duration;

                insertStmt.setString(1, name);
                insertStmt.setString(2, contact);
                insertStmt.setInt(3, villaID);
                insertStmt.setInt(4, duration);
                insertStmt.setString(5, startDateStr);
                insertStmt.setString(6, endDateStr);
                insertStmt.setInt(7, totalPrice);
                insertStmt.executeUpdate();

                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = startDate.plusDays(duration);

                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int reservationID = generatedKeys.getInt(1);
                    Reservation reservation = new Reservation(reservationID, name, contact, villaID, duration, startDate, endDate, totalPrice);
                    rawReservationData.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
    }
    
    //Delete
    public static void deleteReservation(int id) throws SQLException {
        Connection conn = MainDB.connect();
        String query = "DELETE FROM Reservation WHERE reservationID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            rawReservationData.removeIf(Reservation -> Reservation.getReservationID() == id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
    }

    //get villaID
    public static List<Integer> getAllReservationIDs() throws SQLException {
        List<Integer> reservationIDs = new ArrayList<>();
        Connection conn = MainDB.connect();
        String query = "SELECT reservationID FROM Reservation";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reservationIDs.add(rs.getInt("reservationID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
        return reservationIDs;
    }

}
