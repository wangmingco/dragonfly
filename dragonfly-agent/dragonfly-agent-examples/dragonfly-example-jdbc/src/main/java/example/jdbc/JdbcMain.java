package example.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcMain {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMain.class);

    public static void main(String[] args) {
        JDBCService.execJdbc();
    }
}
