package co.wangming.dragonfly.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SimpleJdbc {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/dragonfly";

    private static final String USER = "root";
    private static final String PASS = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            String sql = "SELECT * FROM User limit 1";
            try (ResultSet rs = stmt.executeQuery(sql);) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    System.out.print("User ID: " + id);
                }
            }
        } catch (final Exception se) {
            se.printStackTrace();
        }
    }
}
