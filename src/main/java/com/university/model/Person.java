package com.university.model;

public class Person {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String surName;
    private String phone;
    private String email;
    private Role role;

    public enum Role {
        user,
        admin
    }

    public Person(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public Person(int id, String username, String password, String firstName, String surName, String phone, String email, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }
    public int getId() {
        return id;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getSurName() { return surName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}
