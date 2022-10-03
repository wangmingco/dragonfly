package example.servlet;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.*;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ServletMain {

    private static final Logger logger = LoggerFactory.getLogger(ServletMain.class);

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        logger.info("服务启动中...");

        Server server = new StandardServer();

        server.setCatalinaBase(new File("tmp"));
        server.setCatalinaHome(new File("."));

        Service service = new StandardService();
        service.setName("TomcatServer");
        server.addService(service);

        Connector connector = new Connector("HTTP/1.1");
        connector.setPort(PORT);
        service.addConnector(connector);

        Engine engine = new StandardEngine();
        engine.setName("LocalhostEngine");
        engine.setDefaultHost("localhost");
        service.setContainer(engine);

        Host host = new StandardHost();
        host.setName("localhost");
        host.setAppBase("localhost");
        engine.addChild(host);

        Context rootContext = new StandardContext();
        rootContext.setParent(host);
        rootContext.setName("root");
        // A context path must either be an empty string or start with a '/' and do not end with a '/'. The path [/] does not meet these criteria and has been changed to []
        rootContext.setPath("");
//        context.setDocBase("api");
        rootContext.addLifecycleListener(new Tomcat.FixContextListener());
        host.addChild(rootContext);

        Wrapper wrapper = new StandardWrapper();
        wrapper.setName("ApiServlet");
        wrapper.setServlet(new ApiServlet());

        rootContext.addChild(wrapper);
        rootContext.addServletMappingDecoded("/api", "ApiServlet");

        server.start();

        HttpClient.get("localhost", PORT, "/api");

        server.stop();
        server.destroy();
    }
}
