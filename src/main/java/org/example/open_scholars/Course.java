package org.example.open_scholars;

public class Course {
    private int course_id;
    private String name;
    private String description;
    private int capacity;
    private int fee;
    private String level;
    private String category;
    private int credits;
    private String schedule;
    public static int registration_num;


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



    public int getCourse_id() {
        return course_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFee() {
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
        this.course_id = course_id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public void setFee(int fee){
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

