package example.simple;

public class ExampleMain {

    public static void main(String[] args) {
        System.out.println("ExampleMain start");

        new HelloWorldService().invokePublic(1);
        new HelloWorldService().invokePublic(2);

        HelloWorldService.invokeStaticPublic();
        HelloWorldService.invokeStaticPublic();

        new HelloWorldService().throwException();
    }

}
