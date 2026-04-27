package database;

import java.sql.*;

public class DBConnection{
    private static final String URL = System.getenv().getOrDefault("PRS_DB_URL", "jdbc:mysql://localhost:3306/hospital");
    private static final String USER = System.getenv().getOrDefault("PRS_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("PRS_DB_PASSWORD", "kukshal");

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to database", e);
        }
    }
}
