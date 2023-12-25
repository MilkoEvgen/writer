package org.example.util;

import java.sql.*;

public class DbUtils {
    private static final String URL = "jdbc:mysql://31.31.196.252:3306/u1317084_journal";
    private static final String USERNAME = "u1317084_milko";
    private static final String PASSWORD = "u1317084_milko";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.exit(1);
        }
        return null;
    }

    public static Statement getStatement(String sql) {
        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}