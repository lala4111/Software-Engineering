package com.university.ui;

import com.university.model.Course;
import com.university.service.CourseService;
import javafx.application.Application;
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

//import java.awt.*;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

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
    Boolean isAdmin = true;
    //https://coderanch.com/t/452522/java/set-size-JPanel-Screen-Size
    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenSize = Screen.getPrimary().getBounds().getWidth();
    //double screenSize = 1100;

    double titleFontSize= (int) (screenSize*0.01);
    double textFontSize= (int) (screenSize *0.008);
    //int titleFontSize= 16;
    //int textFontSize= 24;
    Button logOutButton = new Button("Log Out");



    String headerButtonStlye= "-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 10; -fx-border-radius: 10;" +
            "-fx-font-weight:bold; -fx-text-fill: #FFFFFF;"+"-fx-font-size:" +titleFontSize+ "px;";
    String primaryBtnStlye= "-fx-background-color: white; -fx-background-radius: 7; -fx-border-color: #d0baf5;  -fx-padding: 8 16;" +
            "-fx-text-fill: #12012e; -fx-font-size: 18px;-fx-border-radius: 8; -fx-font-weight:bold";
    //rara part
    Manage archive = new Manage();
    TilePane tp1 = new TilePane();



    public TabPane tb () {
        TabPane tabs = new TabPane();
        Tab t1 = new Tab();
        t1.setText("Add Course");
        t1.setClosable(false);
        t1.setContent(tab1());// inset the created tab into the Tab content

        tabs.getTabs().add(t1);


        Tab t2 = new Tab();
        t2.setText("Course Dashboard");
        t2.setClosable(false);
        t2.setContent(tab2());
        tabs.getTabs().add(t2);




        return tabs;
    }









    public Pane tab1(){
        Text id = new Text("Course ID");
        Text name = new Text("Name");
        Text description = new Text("Description");
        Text capacity = new Text("Capacity");
        Text fee = new Text("Fee");
        Text schedule = new Text("Schedule");
        Text level = new Text("Level");
        Text category = new Text("Category");
        Text credit = new Text("Credit");

        TextField tx_id = new TextField();
        TextField tx_name = new TextField();
        TextArea ta_description= new TextArea();
        ta_description.setPrefColumnCount(5);
        ta_description.setPrefRowCount(5);
        ta_description.setWrapText(true);
        TextField tx_capacity = new TextField();
        TextField tx_fee = new TextField();
        TextField tx_schedule = new TextField();
        ChoiceBox<String> cb_level = new ChoiceBox<>();
        cb_level.getItems().addAll("Select","Beginner","Intermediate", "Advanced");
        cb_level.setValue("Select");
        ChoiceBox<String> cb_category = new ChoiceBox<>();
        cb_category.getItems().addAll("Select","Math", "Physics","IT","Art", "Linguistics","Literature","History");
        cb_category.setValue("Select");
        TextField tx_credit = new TextField();


        Button add = new Button("Add");
        Button clear = new Button("Clear");

        add.setOnAction(e->{
            try{

                org.example.open_scholars.Course one = new org.example.open_scholars.Course(
                        Integer.parseInt(tx_id.getText()),
                        tx_name.getText(),ta_description.getText(),
                        Integer.parseInt(tx_capacity.getText()),
                        Integer.parseInt(tx_fee.getText()),
                        cb_level.getValue(),
                        cb_category.getValue(),
                        Integer.parseInt(tx_credit.getText()),
                        tx_schedule.getText()

                );

                archive.setArchive(one);

                tx_id.clear();
                tx_name.clear();
                ta_description.clear();
                tx_capacity.clear();
                tx_fee.clear();
                tx_schedule.clear();
                cb_level.setValue("Select");
                cb_category.setValue("Select");
                tx_credit.clear();





                tp1.setVgap(10);
                tp1.setHgap(10);
                tp1.setPrefColumns(3);

                Rectangle r1 = new Rectangle(100,100);
                r1.setFill(Color.WHITE);
                tp1.getChildren().add(r1);
                r1.setOnMouseClicked(en-> getDetail(one));


            }
            catch(Exception exception){

            }
        });

        clear.setOnAction(e->{

        });



        GridPane gp = new GridPane();
        gp.add(id,0,0);
        gp.add(tx_id,1,0);
        gp.add(name,2,0);
        gp.add(tx_name,3,0);
        gp.add(capacity,4,0);
        gp.add(tx_capacity,5,0);
        gp.add(fee,0,1);
        gp.add(tx_fee,1,1);
        gp.add(schedule,2,1);
        gp.add(tx_schedule,3,1);
        gp.add(credit,4,1);
        gp.add(tx_credit,5,1);
        gp.add(level,0,2);
        gp.add(cb_level,1,2);
        gp.add(category,2,2);
        gp.add(cb_category,3,2);


        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(30));



        VBox vb1 = new VBox();
        vb1.getChildren().addAll(description,ta_description);
        vb1.setSpacing(5);
        vb1.setPadding(new Insets(30));
        vb1.setAlignment(Pos.CENTER);

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(add,clear);
        hb1.setAlignment(Pos.CENTER);
        hb1.setSpacing(200);


        VBox vb2 = new VBox();
        vb2.getChildren().addAll(gp,vb1,hb1);

        Pane p = new Pane();
        p.getChildren().add(vb2);

        return p;
    }

    public Pane tab2(){
        tp1.setHgap(10);
        tp1.setVgap(10);
        tp1.setPadding(new Insets(10));
        return tp1;
    }

    public void getDetail(org.example.open_scholars.Course p){
        Stage stage = new Stage();
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));

        Text t1 = new Text("Course ID: ");
        TextField tx1 = new TextField(String.valueOf(p.getCourse_id()));
        Text t2 =new Text("Course Name: ");
        TextField tx2 = new TextField(p.getName());
        Text t3 =new Text("Capacity: ");
        TextField tx3 = new TextField(String.valueOf(p.getCapacity()));
        Text t4 =new Text("Fee: ");
        TextField tx4 = new TextField(String.valueOf(p.getFee()));
        Text t5 =new Text("Level: ");
        TextField tx5 = new TextField(p.getLevel());
        Text t6 =new Text("Category: ");
        TextField tx6 = new TextField(p.getCategory());
        Text t7 =new Text("Credits: " );
        TextField tx7 = new TextField(String.valueOf(p.getCredits()));
        Text t8 =new Text("Schedule: " );
        TextField tx8 = new TextField(p.getSchedule());
        Text t9 =new Text("Description: ");
        TextField tx9 = new TextField(p.getDescription());

        Button modi = new Button("Modify");

        modi.setOnAction(e->{
            p.setCourse_id(Integer.parseInt(tx1.getText()));
            p.setName(tx2.getText());
            p.setCapacity(Integer.parseInt(tx3.getText()));
            p.setFee(Integer.parseInt(tx4.getText()));
            p.setLevel(tx5.getText());
            p.setCategory(tx6.getText());
            p.setCredits(Integer.parseInt(tx7.getText()));
            p.setSchedule(tx8.getText());
            p.setDescription(tx9.getText());

        });

        gp.add(t1,0,0);
        gp.add(tx1,1,0);
        gp.add(t2,0,1);
        gp.add(tx2,1,1);
        gp.add(t3,0,2);
        gp.add(tx3,1,2);
        gp.add(t4,0,3);
        gp.add(tx4,1,3);
        gp.add(t5,0,4);
        gp.add(tx5,1,4);
        gp.add(t6,0,5);
        gp.add(tx6,1,5);
        gp.add(t7,0,6);
        gp.add(tx7,1,6);
        gp.add(t8,0,7);
        gp.add(tx8,1,7);
        gp.add(t9,0,8);
        gp.add(tx9,1,8);
        gp.add(modi,0,9);

        gp.setVgap(5);
        gp.setHgap(5);
        gp.setAlignment(Pos.CENTER);


        Scene scene = new Scene(gp, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Course Detail");
        stage.show();
    }

    @Override
    public void start(Stage stage) {
        //rara part
        TabPane tabs = tb();

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

                        textArea.setPrefSize(screenSize/7,screenSize/7);
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
                        textArea.setPrefSize(screenSize/7,screenSize/7);
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

        // View Details & Enroll Button: separated??


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
         adminPage.getChildren().add(tabs);
        //adminPage.getChildren().add(new Label("for admins to manage courses and view and manage students registerations"));
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
        Scene scene = new Scene(aLlPages, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Course System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}










