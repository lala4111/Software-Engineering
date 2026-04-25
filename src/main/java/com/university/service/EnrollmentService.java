package com.university.service;

//import com.sun.tools.jconsole.JConsoleContext;
import com.university.database.DBConnection;
import com.university.model.Enrollment;
import javafx.scene.control.TableColumn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    int enrollmentCount;

    // returns a boolean (true/false) indicating success
    public boolean enrollStudent(int personId, int courseId) {
        String sql = "INSERT INTO enrollment (id_student, id_course) VALUES (?, ?)";
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
    public List<Enrollment> getEnrollments(int student_id, int course_id) {
        List<Enrollment> enrollments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM enrollment");
            while (result.next()) {
                int enrollment_id = result.getInt("enrollment_id");
                int courseId= result.getInt("id_course");
                int studentId = result.getInt("student_id");
                Enrollment enrollment= new Enrollment(courseId,studentId);
                enrollments.add(enrollment);
            }

        }
        catch (Exception e) {}
        return  enrollments;

    }
    public int getCourseEnrollmentsCount( int course_id){

        try(Connection connection= DBConnection.getConnection()) {
            String sql = "select count(*) from enrollment where  id_course=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,course_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                enrollmentCount = resultSet.getInt("count(*)");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return enrollmentCount;
    }


    public void addEnrollment(int id_student, int id_course) {
        try(Connection connection= DBConnection.getConnection()) {
            String sql = "INSERT INTO enrollment(id_student,id_course) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_student);
            preparedStatement.setInt(2, id_course);
            preparedStatement.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
