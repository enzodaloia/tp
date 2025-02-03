package org.g4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final String URL = "jdbc:sqlite:/Users/enzo/Documents/cours g4/Cours ingé 1/Java/tp/src/main/resources/sqlite.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection établie haychick");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}