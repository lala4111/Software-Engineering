package com.university.model;

import javafx.beans.property.*;

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
   // SimpleStringProperty Title;
    //SimpleIntegerProperty Id;
    //SimpleStringProperty Seat;
    //SimpleStringProperty Category;
    //SimpleDoubleProperty Fee;
    //SimpleIntegerProperty Credits;
    //SimpleStringProperty Schedule;
    //SimpleLevelProperty Level;
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

    public Course(int id, String title,String category, double fee, int seat, int credits, String schedule, Level level){
        this.id = id;
        this.title = title;
        this.category = category;
        this.seat = seat;
        this.fee = fee;
        this.schedule = schedule;
        this.credits = credits;
        this.level = level;

    }

    public Course (int id, String title, String description, Level level,String category, double fee, int seat, int credits, String schedule){
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.seat = seat;
        this.fee = fee;
        this.schedule = schedule;
        this.credits = credits;
        this.level = level;
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

    //setter
    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setSeat(int seat){
        this.seat = seat;
    }

    public void setFee(int fee){
        this.fee = fee;
    }

    public void setSchedule(String schedule){
        this.schedule = schedule;
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setCredits(int credits){
        this.credits = credits;
    }

    /*public StringProperty titleProperty(){
        return Title;
    }

    public IntegerProperty idProperty(){
        return Id;
    }

    public StringProperty seatNumProperty(){
        return Seat;
    }

    public StringProperty categoryProperty(){
        return Category;
    }*/
}

//testing
