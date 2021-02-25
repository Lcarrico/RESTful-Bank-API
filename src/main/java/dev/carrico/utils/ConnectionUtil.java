package dev.carrico.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection createConnection() {
        String url = System.getenv("BankURL");
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
