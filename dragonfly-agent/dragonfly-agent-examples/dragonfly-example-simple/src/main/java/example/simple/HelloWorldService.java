package example.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldService {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldService.class);

    public void invokePublic(int idx) {
        logger.info("HelloWorldService invokePublic : " + idx);

        invokePrivate();
    }

    private void invokePrivate() {
        logger.info("HelloWorldService invokePrivate");
    }

    public static void invokeStaticPublic() {
        logger.info("HelloWorldService invokeStaticPublic");

        invokeStaticPrivate();
    }

    private static void invokeStaticPrivate() {
        logger.info("HelloWorldService invokePublic");
    }

    public void throwException() {
        throw new RuntimeException("Excepted Exception");
    }
}
