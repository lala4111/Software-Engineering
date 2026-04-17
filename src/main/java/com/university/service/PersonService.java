package com.university.service;

import com.university.database.DBConnection;
import com.university.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonService {


    public void addPerson(int id, String ssn) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "INSERT INTO person (id, ssn) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.setString(2, ssn);

            stmt.executeUpdate();

            System.out.println("Course added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPeople() {

        List<Person> people = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM person");

            while (rs.next()) {
                people.add(new Person(
                        rs.getInt("id"),
                        rs.getString("ssn")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }
}
