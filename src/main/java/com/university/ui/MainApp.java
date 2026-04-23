package com.university.ui;

import com.university.model.Course;
import com.university.model.Person;
import com.university.service.CourseService;
import com.university.service.PersonService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {
    VBox homePage = new VBox(10);
    VBox aboutPage = new VBox(10);
    VBox adminPage = new VBox(10);
    VBox coursesPage = new VBox(10);
    VBox signUpPage = new VBox(10);
    VBox loginPage = new VBox(10);
    VBox coursesboxes = new VBox();
    HBox aboutFooter = new HBox(10);
    Boolean loggedIn = true;
    Boolean sucessfullEnrollment = true;
    Boolean isAdmin = false;
    //https://coderanch.com/t/452522/java/set-size-JPanel-Screen-Size
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int titleFontSize= (int) (screenSize.width *0.01);
    int textFontSize= (int) (screenSize.width *0.008);
    Button logOutButton = new Button("Log Out");



    String headerButtonStlye= "-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 10; -fx-border-radius: 10;" +
            "-fx-font-weight:bold; -fx-text-fill: #FFFFFF;"+"-fx-font-size:" +titleFontSize+ "px;";
    String primaryBtnStlye= "-fx-background-color: white; -fx-background-radius: 7; -fx-border-color: #d0baf5;  -fx-padding: 8 16;" +
            "-fx-text-fill: #12012e; -fx-font-size: 18px;-fx-border-radius: 8; -fx-font-weight:bold";


    @Override
    public void start(Stage stage) {
        List<Course> courses = new CourseService().getCourses();


        BorderPane aLlPages= new BorderPane();

        for(int i =0; i < courses.size(); i++){
                VBox courseBox = new VBox(10);
                Label courseName = new Label("Course Name"+courses.get(i).getTitle());
                courseName.setStyle("fx-text-fill:#12012e;"+ "-fx-font-size:" +titleFontSize+ "px;"+" -fx-font-weight: bold;");

                TextArea courseDescription = new TextArea("Description: "+courses.get(i).getDescription());
                courseDescription.setWrapText(true);
                courseDescription.setMaxWidth(Double.MAX_VALUE);
                courseDescription.setEditable(false);
                courseDescription.setStyle("-fx-font-size:" +textFontSize+ "px;");
                Button enrollBtn = new Button("Enroll");
                Button loginToEnrollBtn = new Button("Login to Enrollment");
                enrollBtn.setStyle("-fx-font-size:" +textFontSize+ "px;");
                enrollBtn.setOnAction(e -> {
                    //https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html

                    if (sucessfullEnrollment) {
                        String message = "You have registered sucessfully, please visit the office and pay the enrollment fees to confirm your seta within 2 days";
                        TextArea textArea = new TextArea(message);
                        textArea.setStyle("-fx-font-size:" +textFontSize+ "px;"+ "-fx-font-weight: bold;");
                        textArea.setWrapText(true);

                        textArea.setPrefSize(screenSize.width/7,screenSize.height/7);
                        //https://code.makery.ch/blog/javafx-dialogs-official/
                        Alert alert =  new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucessfully Enrolled");
                        alert.setHeaderText("Successfully Enrolled");
                        alert.setContentText("You have registered sucessfully, please visit the office and pay the enrollment fees to confirm your seta within 2 days");
                        alert.showAndWait();
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.setStyle("-fx-font-size:" +textFontSize+ "px;"+ "-fx-font-weight: bold;");

                    }else{
                        String message= "The course quota is full";
                        TextArea textArea = new TextArea(message);
                        textArea.setStyle("-fx-font-size:" +textFontSize+ "px;");
                        textArea.setWrapText(true);
                        textArea.setPrefSize(screenSize.width/7,screenSize.height/7);
                        Alert alert =  new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Unsucessfull Enrollment");
                        alert.setHeaderText("Unsucessfull Enrollment");
                        alert.setContentText(textArea.getText());
                        alert.showAndWait();
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.setStyle("-fx-font-size:" +textFontSize+ "px;");


                    }


                });
                enrollBtn.setStyle(primaryBtnStlye);

                loginToEnrollBtn.setStyle(primaryBtnStlye);

                Label quota= new Label("Course Quota "+String.valueOf(courses.get(i).getSeatNum()));
                quota.setStyle("-fx-font-size:" +textFontSize+ "px;");
                if(loggedIn){
                    courseBox.getChildren().addAll(courseName,courseDescription, quota, enrollBtn);
                }else{
                    courseBox.getChildren().addAll(courseName,courseDescription, quota, loginToEnrollBtn);
                }
                loginToEnrollBtn.setOnAction(e -> {aLlPages.setCenter(loginPage);});

                courseBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #d0baf5;  -fx-padding: 15; -fx-border-radius: 10; ");

                coursesboxes.getChildren().add(courseBox);
                coursesboxes.setSpacing(10);
                coursesboxes.setPadding(new Insets(10,10,10,10));
                //coursesboxes.set
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(coursesboxes);

        HBox header = new HBox();
        header.setSpacing(20);
        header.setPadding(new Insets(10,10,30,10));
        header.setSpacing(20);
        header.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 20%, purple 0%, indigo 100%)");

        Button homeBtn = new Button("Home");
        homeBtn.setStyle(headerButtonStlye);
        Button aboutBtn = new Button("About");
        aboutBtn.setStyle(headerButtonStlye );
        Button admindashboardBtn = new Button("Admindashboard");
        admindashboardBtn.setStyle(headerButtonStlye );
        logOutButton.setStyle(headerButtonStlye );
        Button courseBtn = new Button("Courses");
        courseBtn.setStyle(headerButtonStlye );
        Button loginBtn = new Button("Login");
        loginBtn.setStyle(headerButtonStlye );
        Button signupBtn = new Button("Signup");
        signupBtn.setStyle(headerButtonStlye );
        Label userId = new Label("User ID: "+ "tempId111");
        userId.setStyle(headerButtonStlye );



        Button footerHomeBtn = new Button("Home");
        footerHomeBtn.setStyle(headerButtonStlye);
        Button footerAboutBtn = new Button("About");
        footerAboutBtn.setStyle(headerButtonStlye );
        Button footerCourseBtn = new Button("Courses");
        footerCourseBtn.setStyle(headerButtonStlye );

        aboutFooter.setSpacing(20);
        aboutFooter.setPadding(new Insets(10,10,30,10));
        aboutFooter.setSpacing(20);

        aboutFooter.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 20%, purple 0%, indigo 100%)");

        aboutFooter.getChildren().addAll(footerHomeBtn,footerAboutBtn,footerCourseBtn);
        if(isAdmin){
            header.getChildren().addAll(homeBtn,aboutBtn,courseBtn,admindashboardBtn, logOutButton);
        }else{
            if(loggedIn){
                header.getChildren().addAll(homeBtn,aboutBtn, courseBtn, userId, logOutButton);
            }else{
                header.getChildren().addAll(homeBtn,aboutBtn, courseBtn, loginBtn,signupBtn);
            }
        }


        loginPage.getChildren().add(new Label("Login page, user enters his username and password"));
        signUpPage.getChildren().add(new Label("signup page, user enters his username and password and other data to create new account"));
        adminPage.getChildren().add(new Label("for admins to manage courses and view and manage students registerations"));
        coursesPage.getChildren().add(new Label("courses page, user view courses"));
        aboutPage.getChildren().add(new Label("information abouty the orgnaization"));

        homeBtn.setOnAction(e -> {
            aLlPages.setCenter(scrollPane);});
        aboutBtn.setOnAction(e -> {aLlPages.setCenter(aboutPage);});
        loginBtn.setOnAction(e -> {aLlPages.setCenter(loginPage);});
        signupBtn.setOnAction(e -> {aLlPages.setCenter(signUpPage);});
        admindashboardBtn.setOnAction(e -> {aLlPages.setCenter(adminPage);});
        courseBtn.setOnAction(e -> {aLlPages.setCenter(scrollPane);});
        footerHomeBtn.setOnAction(e -> {
            aLlPages.setCenter(scrollPane);});
        footerAboutBtn.setOnAction(e -> {aLlPages.setCenter(aboutPage);});
        footerCourseBtn.setOnAction(e -> {aLlPages.setCenter(coursesPage);});


        scrollPane.setStyle("-fx-border-color: black;");
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setPrefHeight(400);
        aLlPages.setCenter(scrollPane);
        aLlPages.setTop(header);
        aLlPages.setBottom(aboutFooter);
        //Scene scene = new Scene(root, 400, 300);
        Scene scene = new Scene(aLlPages, 1200, 1000);
        stage.setScene(scene);
        stage.setTitle("Course System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}










