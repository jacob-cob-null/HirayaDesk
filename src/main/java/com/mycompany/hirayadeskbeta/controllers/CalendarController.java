package com.mycompany.hirayadeskbeta.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import com.calendarfx.model.*;
import com.calendarfx.view.CalendarView;
import database.ReservationDBcontroller;
import static database.ReservationDBcontroller.rawReservationData;
import database.objects.Reservation;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Jacob
 */
public class CalendarController implements Initializable {

    @FXML
    private CalendarView calendarView;
    @FXML
    private MFXButton refreshCalendar;

    private CalendarSource calendarSource;
    private Calendar calendar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        calendarSource = new CalendarSource("My Calendars");
        calendar = new Calendar("Reservations");
        calendar.setStyle(Calendar.Style.STYLE2);
        calendarSource.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(calendarSource);

        calendarView.setToday(LocalDate.now());
        calendarView.setTime(java.time.LocalTime.now());
        
        refreshCalendar.setOnAction(event -> {
            try {
                loadReservations();
            } catch (SQLException ex) {
                Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void createCalendarEvent(int ID, String name, LocalDate startDate, LocalDate endDate) {
        String entryName = "Reservation of (" + ID + ") " + name;
        Entry<String> event = new Entry<>(entryName);
        event.setInterval(startDate, endDate);
        event.setFullDay(true);

        // Add event to the calendar
        calendar.addEntry(event);
    }

    public void loadReservations() throws SQLException {
        ReservationDBcontroller.mapReservation();
        resetCalendarView();
        for (Reservation res : rawReservationData) {
            int ID = res.getReservationID();
            String name = res.getCustName();
            LocalDate startDate = res.getStartDate();
            LocalDate endDate = res.getEndDate();
            createCalendarEvent(ID, name, startDate, endDate);
        }
    }

    public void resetCalendarView() {
        if (calendar != null) {
            calendar.clear();
        }
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
    }
}
