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
}
