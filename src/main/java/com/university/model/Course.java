package com.university.model;

public class Course {

    private int id;
    private String title;
    private String description;
    private int  seat;
    private int course_id;
    private String name;
    private int capacity;
    private int fee;
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
    public Course(int c, String n, String d, int ca, int fe, String l, String cat, int cred, String sce){
        course_id = c;
        name = n;
        description = d;
        capacity = ca;
        fee = fe;
        level = l;
        category = cat;
        credits = cred;
        schedule = sce;
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
