package com.university.model;

public class Course {

    private int id;
    private String title;
    private String description;
    private int  seat;
    private double fee;
    private String level;
    private String category;
    private int credits;
    private String schedule;
    public static int registration_num;


    public Course(int id, String title,int seat,  String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.seat = seat;
    }
    public Course(int id, String title, String description, int seat, double fee, String level, String category, int credits, String schedule){
        this.id = id;
        this.title = title;
        this.description = description;
        this.seat = seat;
        this.fee = fee;
        this.level = level;
        this.category = category;
        this.credits = credits;
        this.schedule = schedule;
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
