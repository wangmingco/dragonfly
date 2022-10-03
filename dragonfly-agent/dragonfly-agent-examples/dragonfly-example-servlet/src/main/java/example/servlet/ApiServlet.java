package example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;

public class ApiServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ApiServlet.class);

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("ApiServlet 接收到客户端请求");

        try {
            ServletOutputStream out = resp.getOutputStream();
            out.write("HelloWorld".getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("向客户端发送消息异常", e);
        }

	}
}
