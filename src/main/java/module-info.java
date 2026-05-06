module com.university.model {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    //requires jdk.jconsole;


    //opens com.university.model to javafx.fxml;
    opens com.university.model to javafx.base;
    exports com.university.ui;
    opens com.university.ui to javafx.fxml;
}