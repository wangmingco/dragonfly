package example.simple;

public class HelloWorldService {

    public void print() {
        System.out.println("HelloWorldService print -> Hello World");
    }

    public void throwException() {
        throw new RuntimeException("Excepted Exception");
    }
}
