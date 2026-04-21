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
        try (Connection connection = DBConnection.getConnection()) {

            String sql = "INSERT INTO person (id, ssn) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            statement.setString(2, ssn);

            statement.executeUpdate();

            System.out.println("Person added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPeople() {

        List<Person> people = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ssn = resultSet.getString("ssn");
                people.add(new Person(id, ssn));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }
}
