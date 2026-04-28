package com.university.service;

import com.university.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterService {
    public static void AddAccount(String userr, String pass, String name, String sur, String num, String mail){
        try (Connection con = DBConnection.getConnection()) {
            String task = "INSERT INTO person (username, password, name, surname, phone, email, role) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(task);
            ps.setString(1, userr);
            ps.setString(2, pass);
            ps.setString(3, name);
            ps.setString(4, sur);
            ps.setString(5, num);
            ps.setString(6, mail);
            ps.setString(7, "user");

            ps.executeUpdate();
            System.out.println(userr + " added!");
            con.close();
        }catch(Exception e) {
        e.printStackTrace();
        }
}}
