package com.university.ui;

import com.university.model.Course;
import com.university.model.Person;
import com.university.service.CourseService;
import com.university.service.PersonService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        TableView<Course> table = new TableView<>();

        // Columns
        TableColumn<Course, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getId()).asObject()
        );

        TableColumn<Course, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getTitle())
        );

        table.getColumns().addAll(idCol, titleCol);

        Button trying = new Button("Trying");
        Button addBtn = new Button("Add Course");
        TextField titleField = new TextField();
        TextField seatsField = new TextField();
        addBtn.setOnAction(e -> {
            String title = titleField.getText();
            int seats = Integer.parseInt(seatsField.getText());

            new CourseService().addCourse(title, seats);
        });
//push
/*hhhh*/
       
        Button btn = new Button("Load Courses");

        btn.setOnAction(e -> {
            CourseService service = new CourseService();
            ObservableList<Course> data =
                    FXCollections.observableArrayList(service.getCourses());

            table.setItems(data);
        });

        TableView<Person> table2 = new TableView<>();
        TextField personId = new TextField();
        TextField personSsn = new TextField();
        Button loadPersonBtn = new Button("Load Person");
        loadPersonBtn.setOnAction(e -> {
            PersonService service = new PersonService();
            ObservableList<Person> data =
                    FXCollections.observableArrayList(service.getPeople());

            table2.setItems(data);
        });
        Button addPerson= new Button("add person");
        addPerson.setOnAction(e -> {
            int id = Integer.parseInt(personId.getText());
            String ssn = personSsn.getText();
            new PersonService().addPerson(id, ssn);
        });



        VBox root = new VBox(btn, table, table2,  titleField, seatsField, addBtn, loadPersonBtn,  addPerson,  personId, personSsn);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Course System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}