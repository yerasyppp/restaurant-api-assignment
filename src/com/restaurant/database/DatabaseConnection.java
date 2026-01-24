package com.restaurant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/restaurant_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "ra271107";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println(" Успешное подключение к базе данных!");
            }
        } catch (SQLException e) {
            System.err.println(" Ошибка подключения: " + e.getMessage());
        }
    }
}