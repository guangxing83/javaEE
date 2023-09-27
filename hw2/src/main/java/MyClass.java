public class MyClass {
    @InitMethod
    public void init() {
        System.out.println("Init method is called.");
    }

    public void test_no_annotation() {
        System.out.println("No annotation");
    }
}