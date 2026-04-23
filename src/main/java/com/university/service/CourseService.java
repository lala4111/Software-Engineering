package com.university.service;

import com.university.database.DBConnection;
import com.university.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// reference: https://www.tutorialspoint.com/java_mysql/java_mysql_statement.htm, https://www.tutorialspoint.com/java_mysql/java_mysql_result_set_view.htm

public class CourseService {

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            //public abstract java.sql.Statement createStatement()
            //Creates a Statement object for sending SQL statements to the database.

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM courses");
            // Extract data from result set
            //.next() iterates through rows that come off from the database, the first call to the method next makes the first row the current row;
            //the second call makes the second row the current row, and so on.
            while (result.next()) {
                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seats");
                Course course = new Course(course_id, course_name, seat_number, course_description);
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }
    public void addCourse(String title, int seat, String description) {
        try (Connection connection = DBConnection.getConnection()) {
            //? to be determined later
            String insertStatement = "INSERT INTO courses (title, seats, description) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);

            preparedStatement.setString(1, title);// 1 first ? This would set title
            preparedStatement.setInt(2, seat);// 2 second ? This would set seat
            preparedStatement.setString(3, description);
            preparedStatement.executeUpdate();/*Returns the number of rows affected by the execution of the SQL statement. Use this method to execute SQL statements for which you expect to get a number of rows affected - for example, an INSERT, UPDATE, or DELETE statement.*/

            System.out.println("Course added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}