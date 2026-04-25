package com.university.model;

public class Course {

    private int id;
    private String title;
    private String description;
    private int  seat;
    private double fee;
    private String schedule;
    private Level level;
    private String category;
    private int credits;

    public static int registration_num;

    public enum Level {
        beginner,
        intermediate,
        advanced
    }


    public Course(int id, String title,int seat,  String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.seat = seat;
    }
    public Course(int id, String title, String description, int seat, double fee, String schedule, Level level, String category, int credits){
        this.id = id;
        this.title = title;
        this.description = description;
        this.seat = seat;
        this.fee = fee;
        this.schedule = schedule;
        this.level = level;
        this.category = category;
        this.credits = credits;

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
    public double getFee() { return fee;}
    public String getSchedule() { return schedule; }
    public Level getLevel() { return level; }
    public String getCategory() { return category; }
    public int getCredits() { return credits; }
}
