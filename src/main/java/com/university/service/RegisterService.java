package com.university.service;

import com.university.database.DBConnection;

import java.sql.*;

public class RegisterService {
    public static boolean AddAccount(String userr, String pass, String name, String sur, String num, String mail){
        try (Connection con = DBConnection.getConnection()) {
            String task = "INSERT INTO person (username, password, firstName, surname, phone, email, role) VALUES (?,?,?,?,?,?,?)";
            String check = "SELECT 1 FROM person WHERE email = ?";

            PreparedStatement toCheck = con.prepareStatement(check); toCheck.setString(1, mail);
            ResultSet rs = toCheck.executeQuery();
            boolean taken = rs.next();

            if(!taken) {
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
                return true;
            }

            con.close();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
}
}
