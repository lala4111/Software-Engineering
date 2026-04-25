package com.university.model;

public class Person {
    private int id;

    private String username;
    public Person() {}

    public Person(int id, String ssn) {
        this.id = id;
        this.username = ssn;
    }
    public int getId() {
        return id;
    }
}
