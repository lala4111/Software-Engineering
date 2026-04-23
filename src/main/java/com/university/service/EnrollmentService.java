package com.university.service;

import com.university.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EnrollmentService {

    // returns a boolean (true/false) indicating success
    public boolean enrollStudent(int personId, int courseId) {
        String sql = "INSERT INTO enrollments (person_id, course_id) VALUES (?, ?)";
        // ? acts as placeholders for the actual ID
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, personId);
            preparedStatement.setInt(2, courseId);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Enrollment failed: " + e.getMessage());
            return false;
        }
    }
}
