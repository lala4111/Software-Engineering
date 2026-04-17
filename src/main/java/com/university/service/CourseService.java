package com.university.service;

import com.university.database.DBConnection;
import com.university.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseService {

    public List<Course> getCourses() {

        List<Course> courses = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM courses");

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("title")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }
    public void addCourse(String title, int seat) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "INSERT INTO courses (title, seats) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, title);
            stmt.setString(2, String.valueOf(seat));

            stmt.executeUpdate();

            System.out.println("Course added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}