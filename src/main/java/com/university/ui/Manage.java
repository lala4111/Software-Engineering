package com.university.ui;

import org.example.open_scholars.Course;

import java.util.ArrayList;

public class Manage {
    private ArrayList<Course> archive= new ArrayList<>();

    public void  setArchive(Course a){
        archive.add(a);
    }
}
