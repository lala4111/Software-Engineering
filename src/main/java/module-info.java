module org.example.open_scholars {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    //requires jdk.jconsole;


    opens org.example.open_scholars to javafx.fxml;
    exports org.example.open_scholars;
    exports com.university.ui;
    opens com.university.ui to javafx.fxml;
}