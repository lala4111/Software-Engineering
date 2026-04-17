module org.example.open_scholars {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.open_scholars to javafx.fxml;
    exports org.example.open_scholars;
    exports com.university.ui;
}