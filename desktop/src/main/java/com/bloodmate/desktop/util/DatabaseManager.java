package com.bloodmate.desktop.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Properties;

/**
 * Utility class for managing database connections
 */
public class DatabaseManager {
    private static Connection connection;
    private static Properties dbProperties;
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                loadProperties();
                String url = dbProperties.getProperty("db.url");
                String username = dbProperties.getProperty("db.username");
                String password = dbProperties.getProperty("db.password");
                
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.err.println("Error connecting to database: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
    
    private static void loadProperties() {
        if (dbProperties == null) {
            dbProperties = new Properties();
            try (InputStream in = DatabaseManager.class.getResourceAsStream("/database.properties")) {
                if (in != null) {
                    dbProperties.load(in);
                }
            } catch (IOException e) {
                System.err.println("Failed to load database properties: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}