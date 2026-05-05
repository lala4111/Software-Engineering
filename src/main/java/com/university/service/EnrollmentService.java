package com.university.service;

//import com.sun.tools.jconsole.JConsoleContext;
import com.university.database.DBConnection;
import com.university.model.Enrollment;
import javafx.scene.control.TableColumn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {

    public boolean enrollStudent(int studentId, int courseId, Enrollment.PaymentStatus paymentStatus, Enrollment.EnrollmentStatus enrollmentStatus) {
        // Query and lock the specific course row to prevent other threads from accessing it simultaneously
        // "FOR UPDATE" is for locking
        String checkAndLockSql = "SELECT seat FROM course WHERE id = ? FOR UPDATE";
        // Decrease the seat count only if there is at least one seat available
        String decreaseSeatSql = "UPDATE course SET seat = seat - 1 WHERE id = ? AND seat > 0";
        // Insert the new enrollment record into the database
        String insertEnrollmentSql = "INSERT INTO enrollment (id_student, id_course, payment_status, enrollment_status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            // Disable auto-commit to start a manual Database Transaction
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkAndLockSql);
                 PreparedStatement updateStmt = conn.prepareStatement(decreaseSeatSql);
                 PreparedStatement insertStmt = conn.prepareStatement(insertEnrollmentSql)) {

                // 1. Check seat availability and apply a lock
                checkStmt.setInt(1, courseId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int currentSeats = rs.getInt("seat");
                    if (currentSeats <= 0) {
                        // No seats left: rollback the transaction and return false
                        conn.rollback();
                        System.out.println("No seats available for course ID: " + courseId);
                        return false;
                    }
                } else {
                    // Course ID not found in the database
                    conn.rollback();
                    return false;
                }

                //  2. Decrease the seat count in the course table
                updateStmt.setInt(1, courseId);
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated == 0) {
                    // Update failed : rollback the transaction
                    conn.rollback();
                    return false;
                }

                // 3. Insert the enrollment record
                insertStmt.setInt(1, studentId);
                insertStmt.setInt(2, courseId);
                insertStmt.setString(3, paymentStatus.name());
                insertStmt.setString(4, enrollmentStatus.name());
                insertStmt.executeUpdate();

                // Commit the changes permanently to the database
                conn.commit();
                return true;

            } catch (SQLException e) {
                // If any error occurs, rollback all changes
                conn.rollback();
                System.err.println("Transaction failed, rolling back: " + e.getMessage());
                return false;
            } finally {
                // Restore default auto commit behavior for the connection
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Enrollment> getEnrollments(Integer studentId, Integer courseId) {
        // use object wrapped integers so that they could be null
        List<Enrollment> enrollments = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM enrollment WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (studentId != null) {
            sql.append(" AND id_student = ?");
            params.add(studentId);
        }
        if (courseId != null) {
            sql.append(" AND id_course = ?");
            params.add(courseId);
        }
        try (Connection conn = DBConnection.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int enrollmentId = result.getInt("enrollmentId");
                int currentCourseId = result.getInt("id_course");
                int currentStudentId = result.getInt("id_student");
                Enrollment.PaymentStatus paymentStatus = Enrollment.PaymentStatus.valueOf(result.getString("payment_status"));
                Enrollment.EnrollmentStatus enrollmentStatus = Enrollment.EnrollmentStatus.valueOf(result.getString("enrollment_status"));
                Enrollment enrollment= new Enrollment(enrollmentId, currentCourseId, currentStudentId, paymentStatus, enrollmentStatus);
                enrollments.add(enrollment);
            }

        }
        catch (Exception e) {System.err.println("Fetch enrollments failed: " + e.getMessage());}
        return  enrollments;

    }
    public int getCourseEnrollmentsCount( int course_id){
        int enrollmentCount = 0;
        // Moving this variable inside the method to make it a Local Variable.
        // each concurrent Thread now receives its own isolated copy, guaranteeing Thread Safety.
        try(Connection connection= DBConnection.getConnection()) {
            String sql = "select count(*) from enrollment where  id_course=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,course_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            // always returns exactly one row, so an 'if' statement is sufficient instead of a 'while' loop
            if (resultSet.next()) {
                enrollmentCount = resultSet.getInt(1); // 这里的 1 代表获取第一列的结果
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return enrollmentCount;
    }

    public boolean updateEnrollmentStatus(int enrollmentId, Enrollment.PaymentStatus paymentStatus, Enrollment.EnrollmentStatus enrollmentStatus) {
        StringBuilder sql = new StringBuilder("UPDATE enrollment SET ");
        List<Object> params = new ArrayList<>();

        if (paymentStatus != null) {
            sql.append("payment_status = ?, ");
            params.add(paymentStatus.name());
        }
        if (enrollmentStatus != null) {
            sql.append("enrollment_status = ?, ");
            params.add(enrollmentStatus.name());
        }
        // remove the comma and space
        sql.setLength(sql.length() - 2);

        sql.append(" WHERE enrollmentId = ?");
        params.add(enrollmentId);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            System.err.println("Update enrollment failed: " + e.getMessage());
            return false;
        }
    }

    /*public void addEnrollment(int id_student, int id_course) {
        try(Connection connection= DBConnection.getConnection()) {
            String sql = "INSERT INTO enrollment(id_student,id_course) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_student);
            preparedStatement.setInt(2, id_course);
            //preparedStatement.setString(3, payment_status.name());
            //preparedStatement.setString(4, enrollment_status.name());
            preparedStatement.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/


//    public void addEnrollment(int id_student, int id_course, Enrollment.PaymentStatus payment_status, Enrollment.EnrollmentStatus enrollment_status) {
//        try(Connection connection= DBConnection.getConnection()) {
//            String sql = "INSERT INTO enrollment(id_student,id_course, payment_status, enrollment_status) VALUES (?, ?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, id_student);
//            preparedStatement.setInt(2, id_course);
//            preparedStatement.setString(3, payment_status.name());
//            preparedStatement.setString(4, enrollment_status.name());
//            preparedStatement.execute();
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


}
