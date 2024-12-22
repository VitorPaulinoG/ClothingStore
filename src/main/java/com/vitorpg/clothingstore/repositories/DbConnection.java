package com.vitorpg.clothingstore.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL_DB = "jdbc:postgresql://localhost:5433/dbclothingstore";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DbConnection.URL_DB, DbConnection.USER, DbConnection.PASSWORD);
            if (conn != null) {
                System.out.println("Connected to database #1");
            }
        } catch (SQLException e) {
            System.out.println("Error when connecting...: " + e);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return conn;
    }
}
