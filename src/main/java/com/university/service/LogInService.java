package com.university.service;

import com.university.database.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LogInService {

    public static boolean CheckPassword(String user, String psw){
        try (Connection con = DBConnection.getConnection()) { // TODO: Need to load id and profile..
            String task = String.format("SELECT password FROM person WHERE username LIKE \"%s\"", user);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(task);
            rs.next();
            if(psw.equals(rs.getString(1))) {
                System.out.println("Logged in :3");
                return true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
