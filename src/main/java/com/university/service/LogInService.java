package com.university.service;

import com.university.database.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LogInService {

    public static boolean CheckPassword(String user, String psw){
        try (Connection con = DBConnection.getConnection()) { // TODO: Need to load id and profile..
            //currently username in DB is not unique, If 2 users share same username and password, system logs the oldest user in the DB
            //opt1:restrict username in Database, opt2: login with email+ pass, tested works(commented code)
            String task = String.format("SELECT password FROM person WHERE username LIKE \"%s\"", user);
            //String task = String.format("SELECT password FROM person WHERE email LIKE \"%s\"", user);
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
    public static int GetID(String user){
        try (Connection con = DBConnection.getConnection()) {
            String task = String.format("SELECT id FROM person WHERE username LIKE \"%s\"", user);
            //String task = String.format("SELECT id FROM person WHERE email LIKE \"%s\"", user);//opt2 no errors
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(task);
            rs.next();
            return Integer.parseInt(rs.getString(1));
        } catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    public static boolean GetPrivilege(String user){
        try (Connection con = DBConnection.getConnection()){
            String task = String.format("SELECT role FROM person WHERE username LIKE \"%s\"", user);
            //String task = String.format("SELECT role FROM person WHERE email LIKE \"%s\"", user);//opt2 no errors
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(task);
            rs.next();
            if(rs.getString(1).equals("admin")) return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
