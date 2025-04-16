/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database.objects;

/**
 *
 * @author Jacob
 */
public class Villa {
    int villaID;
    String tierID;
    boolean availability;

    public Villa(int villaID, String tierID, boolean availability){
        this.villaID = villaID;
        this.tierID = tierID;
        this.availability = availability;
    }

    public int getVillaID() {
        return villaID;
    }

    public String getTierID() {
        return tierID;
    }

    public boolean isAvailable() {
        return availability;
    }
}