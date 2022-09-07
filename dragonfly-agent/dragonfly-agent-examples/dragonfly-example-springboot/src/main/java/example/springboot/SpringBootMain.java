package example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringBootMain {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootMain.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootMain.class);

        TimeUnit.SECONDS.sleep(2);

        request();
    }

    private static void request() throws IOException {
        URL url = new URL("http://localhost:8080/api/hello");
        HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        connection.setDoOutput(false);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setUseCaches(true);
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(3000);
        connection.connect();
        int code = connection.getResponseCode();

        System.out.println("code : " + code);
    }
}
