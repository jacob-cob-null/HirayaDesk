/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import static database.VillaDBcontroller.rawVillaData;
import static database.VillaDBcontroller.updateVillaStatus;
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
                        villaID, duration, startDate, endDate, price, addStatus(endDate)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
    }

    //Add status
    public static String addStatus(LocalDate endDate) {
        String status;
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isBefore(endDate)) {
            status = "ongoing";
        } else {
            status = "complete";
        }
        return status;
    }

    //Check status
    public static void updateStatus() throws SQLException {
        for (Reservation res : rawReservationData) {
            if (res.getStatus() == "complete") {
                updateVillaStatus(res.getVillaID(), 0);
            }
            res.setStatus(addStatus(res.getEndDate()));
        }
    }

    // CREATE Reservation Record
    public static void createReservation(String name, String contact, int villaID, int duration, LocalDate startDate) {
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
                LocalDate endDate = startDate.plusDays(duration);

                insertStmt.setString(1, name);
                insertStmt.setString(2, contact);
                insertStmt.setInt(3, villaID);
                insertStmt.setInt(4, duration);
                insertStmt.setString(5, startDate.toString());  // Start date in yyyy-MM-dd format
                insertStmt.setString(6, endDate.toString());    // End date in yyyy-MM-dd format
                insertStmt.setInt(7, totalPrice);
                insertStmt.executeUpdate();

                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int reservationID = generatedKeys.getInt(1);
                    Reservation reservation = new Reservation(reservationID, name, contact, villaID, duration, startDate, endDate, totalPrice, addStatus(endDate));
                    rawReservationData.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MainDB.closeConnection(conn);
        }
    }

    public static void updateReservation(int reservationID, String name, String contact, int villaID, int duration, LocalDate startDate) {
        Connection conn = MainDB.connect();
        String priceQuery = "SELECT t.price FROM Villa v JOIN Tier t ON v.tierID = t.tierID WHERE v.villaID = ?";
        String updateQuery = "UPDATE Reservation SET custName = ?, custContactNumber = ?, villaID = ?, duration = ?, startDate = ?, endDate = ?, price = ? WHERE reservationID = ?";

        try (
                PreparedStatement priceStmt = conn.prepareStatement(priceQuery); PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
            priceStmt.setInt(1, villaID);
            ResultSet rs = priceStmt.executeQuery();

            if (rs.next()) {
                int pricePerDay = rs.getInt("price");
                int totalPrice = pricePerDay * duration;
                LocalDate endDate = startDate.plusDays(duration);

                updateStmt.setString(1, name);
                updateStmt.setString(2, contact);
                updateStmt.setInt(3, villaID);
                updateStmt.setInt(4, duration);
                updateStmt.setString(5, startDate.toString());
                updateStmt.setString(6, endDate.toString());
                updateStmt.setInt(7, totalPrice);
                updateStmt.setInt(8, reservationID);
                updateStmt.executeUpdate();

                for (Reservation res : rawReservationData) {
                    if (res.getReservationID() == reservationID) {
                        res.setCustName(name);
                        res.setCustContactNumber(contact);
                        res.setVillaID(villaID);
                        res.setDuration(duration);
                        res.setStartDate(startDate);
                        res.setEndDate(endDate);
                        res.setPrice(totalPrice);
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

    //get reservationID
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

    //get reservation count
    public static int getReservationCount() throws SQLException {
        Connection conn = MainDB.connect();
        String query = "SELECT COUNT(reservationID) AS Count FROM Reservation";
        int count = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("Count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    //get total revenue
    public static int getTotalRevenue() throws SQLException {
        Connection conn = MainDB.connect();
        String query = "SELECT SUM(price) AS TotalRevenue FROM Reservation";
        int sum = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sum = rs.getInt("TotalRevenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    //get ongoing reservation
    public static int getOngoingReservation() {
        int count = 0;
        for (Reservation res : rawReservationData) {
            if ("ongoing".equals(res.getStatus())) {
                count++;
            }
        }
        return count;
    }

    //get room tier distribution, join reservation and villa tables
    public static int[] getTierDistribution() throws SQLException {
        Connection conn = MainDB.connect();
        String query = "SELECT \n"
                + "    v.tierID,\n"
                + "    COUNT(r.villaID) AS tierCount\n"
                + "FROM Villa v \n"
                + "JOIN Reservation r ON v.villaID = r.villaID\n"
                + "GROUP BY v.tierID;";
        int[] tierDist = new int[3];
        int index = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tierDist[index] = rs.getInt("tierCount");
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tierDist;
    }
}
