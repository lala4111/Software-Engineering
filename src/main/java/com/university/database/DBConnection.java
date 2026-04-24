package com.university.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/university";
    private static final String USER = "root";
    private static final String PASSWORD = "DOGCRAPLOL";

    public static Connection getConnection() throws Exception {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
}
