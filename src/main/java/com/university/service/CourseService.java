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
            ResultSet result = statement.executeQuery("SELECT * FROM course");
            // Extract data from result set
            //.next() iterates through rows that come off from the database, the first call to the method next makes the first row the current row;
            //the second call makes the second row the current row, and so on.
            while (result.next()) {
                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seat");
                double  fee=result.getDouble("fee");
                String schedule= result.getString("schedule");
                //Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule, category, credits );
                //Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule, level, category, credits );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public void addCourse(String title, String description, int seat, double fee,  String category,int credits, String schedule) {
        try (Connection connection = DBConnection.getConnection()) {
            //? to be determined later
            String insertStatement = "INSERT INTO course (title ,seat, description, fee, category,credits,schedule) VALUES (?, ?, ?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);

            preparedStatement.setString(1, title);// 1 first ? This would set title
            preparedStatement.setInt(2, seat);// 2 second ? This would set seat
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, fee);
            preparedStatement.setString(5, category);
            preparedStatement.setInt(6, credits);
            preparedStatement.setString(7, schedule);
            preparedStatement.executeUpdate();/*Returns the number of rows affected by the execution of the SQL statement. Use this method to execute SQL statements for which you expect to get a number of rows affected - for example, an INSERT, UPDATE, or DELETE statement.*/

            System.out.println("Course added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCourse(String title, String description, int seat, double fee, String schedule, Course.Level level, String category, int credits) {
        try (Connection connection = DBConnection.getConnection()) {
            //? to be determined later
            String insertStatement = "INSERT INTO course (title ,seat, description, fee, schedule, level, category, credits) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);

            preparedStatement.setString(1, title);// 1 first ? This would set title
            preparedStatement.setInt(2, seat);// 2 second ? This would set seat
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, fee);
            preparedStatement.setString(5, schedule);
            preparedStatement.setString(6, level.name());
            preparedStatement.setString(7, category);
            preparedStatement.setInt(8, credits);
            preparedStatement.executeUpdate();/*Returns the number of rows affected by the execution of the SQL statement. Use this method to execute SQL statements for which you expect to get a number of rows affected - for example, an INSERT, UPDATE, or DELETE statement.*/

            System.out.println("Course added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}