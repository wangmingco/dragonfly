package example.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMain {

    private static final Logger logger = LoggerFactory.getLogger(ExampleMain.class);

    public static void main(String[] args) {
        logger.info("ExampleMain start. class dump folder: " + System.getProperty("net.bytebuddy.dump"));

        new HelloWorldService().invokePublic(1);
        new HelloWorldService().invokePublic(2);

        HelloWorldService.invokeStaticPublic();
        HelloWorldService.invokeStaticPublic();

        new HelloWorldService().throwException();
    }

}
