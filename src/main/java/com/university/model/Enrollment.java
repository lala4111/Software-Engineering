package com.university.model;

public class Enrollment {
    private int course_id;
    private int student_id;
    private int enrollment_id;
    public  Enrollment(int course_id, int student_id) {
        this.course_id = course_id;
        this.student_id = student_id;
    }
    public int getCourse_id() {
        return course_id;
    }
    public void setCourse_id(int course_id) {}
    public int getStudent_id() {
        return student_id;
    }
}
