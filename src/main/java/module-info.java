module org.example.open_scholars {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    //requires jdk.jconsole;
    //push
    opens com.university.model to javafx.base;
    exports com.university.ui;
    opens com.university.ui to javafx.fxml;
}