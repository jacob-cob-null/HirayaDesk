/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database.objects;

import java.time.LocalDate;

/**
 *
 * @author Jacob
 */
public class Reservation {

    int reservationID;
    String custName;
    String custContactNumber;
    int villaID;
    int duration;
    LocalDate startDate;
    LocalDate endDate;
    int price;

    public Reservation(int reservationID, String custName, String custContactNumber, int villaID,
            int duration, LocalDate startDate, LocalDate endDate, int price) {

        this.reservationID = reservationID;
        this.custName = custName;
        this.custContactNumber = custContactNumber;
        this.villaID = villaID;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    //getters
    public int getReservationID() {
        return reservationID;
    }

    public String getCustName() {
        return custName;
    }

    public String getCustContactNumber() {
        return custContactNumber;
    }

    public int getVillaID() {
        return villaID;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getPrice() {
        return price;
    }

    //setters
    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setCustContactNumber(String custContactNumber) {
        this.custContactNumber = custContactNumber;
    }

    public void setVillaID(int villaID) {
        this.villaID = villaID;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
