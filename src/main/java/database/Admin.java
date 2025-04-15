/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author Jacob
 */
public class Admin {

    String username;
    String password;
    String gmail;

    public Admin(String username, String password, String gmail) {
        this.username = username;
        this.password = password;
        this.gmail = gmail;
    }

    //GETTER
    public String getUserName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getGmail() {
        return this.gmail;
    }
}
