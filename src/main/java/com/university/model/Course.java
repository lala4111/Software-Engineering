package com.university.model;

public class Course {
    private int id;
    private String title;
    private String description;
    private int seat;
    private double fee;
    private String level;
    private String category;
    private int credits;
    private String schedule;
    public static int registration_num;
    //

    public Course(int id, String title,int seat,  String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.seat = seat;
    }

    public Course(int c, String t, String d, int s, double fe, String l, String cat, int cred, String sce){
        id = c;
        title = t;
        description = d;
        seat = s;
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

    public String getDescription() {
        return description;
    }

    public int getSeatNum() {
        return seat;
    }

    public double getFee() {
        return fee;
    }

    public String getLevel() {
        return level;
    }

    public String getCategory() {
        return category;
    }

    public int getCredits() {
        return credits;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setCourse_id(int course_id) {
        this.id = course_id;
    }

    public void setName(String name){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCapacity(int seat){
        this.seat = seat;
    }

    public void setFee(double fee){
        this.fee = fee;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getRegistration_num() {
        return registration_num;
    }


}

