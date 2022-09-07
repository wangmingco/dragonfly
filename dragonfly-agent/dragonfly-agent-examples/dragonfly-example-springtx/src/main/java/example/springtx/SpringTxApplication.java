package example.springtx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class SpringTxApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringTxApplication.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(SpringTxApplication.class, args);
		TransactionService executor = context.getBean(TransactionService.class);
		executor.exec();
	}

}