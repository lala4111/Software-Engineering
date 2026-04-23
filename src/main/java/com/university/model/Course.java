package com.university.model;

public class Course {

    private int id;
    private String title;
    private String description;
    private int  seat;


    public Course(int id, String title,int seat,  String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {return description;}
    public int getSeatNum(){
        return seat;
    }
}
