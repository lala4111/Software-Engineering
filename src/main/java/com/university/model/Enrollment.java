package com.university.model;

import java.security.PrivateKey;

public class Enrollment {
    private int enrollmentId;
    private int id_course;
    private int id_student;
    private PaymentStatus payment_status;
    private EnrollmentStatus enrollment_status;
    private String courseName;
    private String studentFirstName;
    private String studentLastName;

    public enum PaymentStatus {
        unpaid,
        paid
    }

    public enum EnrollmentStatus {
        pending,
        enrolled,
        completed,
        dropped
    }


    public  Enrollment(int enrollmentId, int id_course, int id_student, PaymentStatus payment_status, EnrollmentStatus enrollment_status) {
        this.enrollmentId = enrollmentId;
        this.id_course = id_course;
        this.id_student = id_student;
        this.payment_status = payment_status;
        this.enrollment_status = enrollment_status;
    }
    public  Enrollment(int enrollmentId, int id_course, int id_student, PaymentStatus payment_status,
                       EnrollmentStatus enrollment_status, String courseName, String  studentFirstName, String studentLastName) {
        this.enrollmentId = enrollmentId;
        this.id_course = id_course;
        this.id_student = id_student;
        this.payment_status = payment_status;
        this.enrollment_status = enrollment_status;
        this.courseName = courseName;
        this.studentFirstName=studentFirstName;
        this.studentLastName=studentLastName;
    }
    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }
    public int getId_Course() {
        return id_course;
    }
    public void setId_Course(int id_course) {this.id_course = id_course;}
    public int getId_Student() {
        return id_student;
    }
    public String getStudentFirstName() {
        return studentFirstName;
    }
    public String getStudentLastName() {
        return studentLastName;
    }

    public String getCourseName() { return courseName; }
    public void setEnrollment_status(EnrollmentStatus enrollment_status) {
        this.enrollment_status = enrollment_status;
    }
    public void setPayment_status(PaymentStatus payment_status) {
        this.payment_status = payment_status;
    }
    public PaymentStatus getPayment_status() { return payment_status; }
    public EnrollmentStatus getEnrollment_status() { return enrollment_status; }

}
