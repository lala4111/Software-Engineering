package com.university.model;

public class Course {

    private int id;
    private String title;
    private int seats;

    public Course(int id, String title, int course_seats) {
        this.id = id;
        this.title = title;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getSeats() { return seats; }
}
