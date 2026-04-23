package org.example.open_scholars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


import java.io.IOException;

public class HelloApplication extends Application {
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

                Course one = new Course(
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

    public void getDetail(Course p){
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











    public void start(Stage stage) throws IOException {
        TabPane tabs = tb();
        Scene scene = new Scene(tabs, 800, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}

