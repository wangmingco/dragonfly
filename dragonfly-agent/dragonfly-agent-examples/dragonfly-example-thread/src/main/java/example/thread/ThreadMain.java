package example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadMain {

    private static final Logger logger = LoggerFactory.getLogger(ThreadMain.class);

    public static void main(String[] args) throws InterruptedException {

        new ThreadMain().exec();
    }

    private void exec() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        threadPoolExecutor.submit(() -> {
            logger.info("thread exec");
        });

    }

}
