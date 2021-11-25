package example.simple;

public class HelloWorldService {

    public void invokePublic() {
        System.out.println("HelloWorldService invokePublic");

        invokePrivate();
    }

    private void invokePrivate() {
        System.out.println("HelloWorldService invokePrivate");
    }

    public static void invokeStaticPublic() {
        System.out.println("HelloWorldService invokeStaticPublic");

        invokeStaticPrivate();
    }

    private static void invokeStaticPrivate() {
        System.out.println("HelloWorldService invokePublic");
    }

    public void throwException() {
        throw new RuntimeException("Excepted Exception");
    }
}
