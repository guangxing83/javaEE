import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.io.*;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SimpleTest {
    @Test
    public void testSuccessfulInitialization() {
        System.out.println("Testing successful object creation and initialization:");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        // 执行Main.main方法
        Main.main(null);

        // 恢复System.out
        System.setOut(originalOut);

        // 获取捕获的输出内容
        String output = outputStream.toString();

        // 添加断言以验证输出是否包含预期的内容
        assertEquals("Init method is called.\n", output);
    }

    @Test
    public void testClassNotFound() {
        assertThrows(ClassNotFoundException.class, () -> {
            Main.createInstance("non_existent");
        });
    }

    @Test
    public void testInitMethodAnnotation() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            PrintStream originalOut = System.out;
            System.setOut(printStream);

            Object obj = Main.createInstance("MyClass"); // 替换为您的类名
            assertNotNull(obj);

            // 使用反射查找并调用带有@InitMethod注解的方法
            Class<?> clazz = obj.getClass();
            for (Method method : clazz.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(InitMethod.class)) {
                    method.setAccessible(true);
                    method.invoke(obj);
                }
            }
            // 恢复System.out
            System.setOut(originalOut);

            // 获取捕获的输出内容
            String output = outputStream.toString();

            // 添加断言以验证输出是否包含预期的内容
            assertEquals("No annotation\n", output);
        } catch (Exception e) {
            fail("Object creation and init methods invocation should not throw an exception.");
        }
    }
}
