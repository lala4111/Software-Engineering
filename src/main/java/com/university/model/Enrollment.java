package com.university.model;

public class Enrollment {
    private int id_course;
    private int id_student;
    private PaymentStatus payment_status;
    private EnrollmentStatus enrollment_status;

    public enum PaymentStatus {
        unpayed,
        payed
    }

    public enum EnrollmentStatus {
        pending,
        enrolled,
        completed,
        dropped
    }


    public  Enrollment(int id_course, int id_student, PaymentStatus payment_status, EnrollmentStatus enrollment_status) {
        this.id_course = id_course;
        this.id_student = id_student;
        this.payment_status = payment_status;
        this.enrollment_status = enrollment_status;
    }
    public int getId_Course() {
        return id_course;
    }
    public void setId_Course(int id_course) {}
    public int getId_Student() {
        return id_student;
    }
    public PaymentStatus getPayment_status() { return payment_status; }
    public EnrollmentStatus getEnrollment_status() { return enrollment_status; }

    /*public Enrollment(int id_course, int id_student) {
        this.id_course = id_course;
        this.id_student = id_student;
    }*/
}
