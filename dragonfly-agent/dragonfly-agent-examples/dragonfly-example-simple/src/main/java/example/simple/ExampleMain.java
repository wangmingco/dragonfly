package example.simple;

public class ExampleMain {

    public static void main(String[] args) {
        System.out.println("ExampleMain start");

        new HelloWorldService().invokePublic();
        HelloWorldService.invokeStaticPublic();

        new HelloWorldService().throwException();
    }

}
