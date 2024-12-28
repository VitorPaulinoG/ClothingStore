package com.vitorpg.clothingstore.repositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static final String CONFIG_FILE = "src/main/resources/com/vitorpg/clothingstore/dbconfig.properties";
    private static String user;
    private static String password;
    private static String url;

    private static String currentEnvironment;

    public static void loadConfig (String environment) {
        currentEnvironment = environment;
        loadConfig();
    }
    private static void loadConfig () {
        Properties props = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE)) {
            props.load(fileInputStream);
            user = props.getProperty(currentEnvironment + ".user");
            password = props.getProperty(currentEnvironment + ".password");
            url = props.getProperty(currentEnvironment + ".url");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error when connecting...: " + e);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return conn;
    }

}
