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


    public void addPerson(int id, String username, String password, String firstName, String surName, String phone, String email, Person.Role role) {
        try (Connection connection = DBConnection.getConnection()) {

            String sql = "INSERT INTO person (id, username, password, firstName, surName, phone, email, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, firstName);
            statement.setString(5, surName);
            statement.setString(6, phone);
            statement.setString(7, email);
            statement.setString(8, role.name());

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
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String surName = resultSet.getString("surName");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                Person.Role role = Person.Role.valueOf(resultSet.getString("role"));
                people.add(new Person(id, username,  password, firstName, surName, phone, email, role));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }
}
