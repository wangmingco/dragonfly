package example.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SimpleJdbc {

    private static final Logger logger = LoggerFactory.getLogger(SimpleJdbc.class);

    private static final String DB_URL = "jdbc:mysql://localhost:3306/dragonfly";

    private static final String USER = "root";
    private static final String PASS = "root";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        logger.info("JDBC Demo开始运行");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            String sql = "SELECT * FROM User limit 1";
            try (ResultSet rs = stmt.executeQuery(sql);) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    logger.info("User ID: {}", id);
                }
            }
        } catch (final Exception se) {
            logger.error("发生异常", se);
        }
    }
}
