package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (var input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new SQLException("Unable to find db.properties");
            }
            props.load(input);
        } catch (Exception e) {
            throw new SQLException("Failed to load db properties", e);
        }
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.username"),
                props.getProperty("db.password")
        );
    }
}