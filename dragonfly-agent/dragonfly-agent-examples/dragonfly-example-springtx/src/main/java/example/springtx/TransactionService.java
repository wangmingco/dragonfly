package example.springtx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created By WangMing On 2018/7/6
 **/
@Component
public class TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void exec() {
		logger.info("JDBC 查询开始");

		Long id = jdbcTemplate.queryForObject("select id from user limit 1", Long.class);

		logger.info("JDBC 查询结果:{}", id);
	}

}
