package com.university.service;

import com.university.database.DBConnection;
import com.university.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

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
                Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                Course course = new Course(course_id,course_name, course_description,level,category,fee, seat_number, credits ,schedule);
                //Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule, level, category, credits );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
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
///
    public List<Course> filterByCategory(String categor) {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM course WHERE category = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, categor);

            ResultSet result = statement.executeQuery();
            while (result.next()) {

                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seat");
                double  fee=result.getDouble("fee");
                String schedule= result.getString("schedule");
                Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule,level, category, credits );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;

    }
    // filter by priceRange
    public List<Course> filterByPrice(AtomicReference<Double> min, AtomicReference<Double> max) {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM course WHERE fee > ? and fee < ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setDouble(1, min.get().doubleValue());
            statement.setDouble(2, max.get().doubleValue());

            ResultSet result = statement.executeQuery();
            while (result.next()) {

                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seat");
                double  fee=result.getDouble("fee");
                String schedule= result.getString("schedule");
                Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule,level, category, credits );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;

    }
    // filter by level
    public List<Course> filterByLevel(String leve) {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM course WHERE level=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, leve.toLowerCase());

            ResultSet result = statement.executeQuery();
            while (result.next()) {

                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seat");
                double  fee=result.getDouble("fee");
                String schedule= result.getString("schedule");
                Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule,level, category, credits );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;

    }





    //filter by both category and fee
    public List<Course> filterByCategoryFee(String categor, AtomicReference<Double> min, AtomicReference<Double> max) {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM course WHERE category = ? and fee > ? and fee < ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, categor);
            statement.setDouble(2, min.get().doubleValue());
            statement.setDouble(3, max.get().doubleValue());

            ResultSet result = statement.executeQuery();
            while (result.next()) {

                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seat");
                double  fee=result.getDouble("fee");
                String schedule= result.getString("schedule");
                Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule,level, category, credits );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;

    }

    public List<Course> filterByCategoryFeeLevel(String categor, double min, double max, String leve) {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM course WHERE category = ? and fee between ? and ? and level=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, categor);
            statement.setDouble(2,min);
            statement.setDouble(3,max);
            statement.setString(4, leve.toLowerCase());

            ResultSet result = statement.executeQuery();
            while (result.next()) {

                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seat");
                double  fee=result.getDouble("fee");
                String schedule= result.getString("schedule");
                Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule,level, category, credits );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;

    }

    public List<Course> filterByCategoryLevel(String categor,  String leve) {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM course WHERE category = ? and level=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, categor);
            statement.setString(2, leve.toLowerCase());

            ResultSet result = statement.executeQuery();
            while (result.next()) {

                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seat");
                double  fee=result.getDouble("fee");
                String schedule= result.getString("schedule");
                Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule,level, category, credits );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;

    }
   // filter by price and level
    public List<Course> filterByFeeLevel( double min, double max, String leve) {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM course WHERE fee between ? and ? and level=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setDouble(1,min);
            statement.setDouble(2,max);
            statement.setString(3, leve.toLowerCase());

            ResultSet result = statement.executeQuery();
            while (result.next()) {

                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seat");
                double  fee=result.getDouble("fee");
                String schedule= result.getString("schedule");
                Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                Course course = new Course(course_id,course_name, course_description, seat_number,fee,schedule,level, category, credits );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;

    }

    public Course searchCourse(String titl) {
        //List<Course> courses = new ArrayList<>();
        Course course = null;
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM course WHERE title = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            //statement.setInt(1, id);
            statement.setString(1,titl);

            ResultSet result = statement.executeQuery();
            while (result.next()) {

                int course_id = result.getInt("id");//retrieve by column name
                String course_name = result.getString("title");//retrieve by column name
                String course_description = result.getString("description");
                int seat_number = result.getInt("seat");
                double  fee=result.getDouble("fee");
                String schedule= result.getString("schedule");
                Course.Level level=Course.Level.valueOf(result.getString("level"));
                String category=result.getString("category");
                int credits=result.getInt("credits");

                course = new Course(course_id,course_name, course_description, seat_number,fee,schedule,level, category, credits );
                //courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;

    }



    public void deleteCourse(int id){
        try (Connection connection = DBConnection.getConnection()) {
            //? to be determined later
            String sql = "delete from course where  id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}