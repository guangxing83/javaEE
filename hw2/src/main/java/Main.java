import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            // 读取属性文件
            Properties properties = loadProperties();

            // 获取配置的类名
            String className = properties.getProperty("bootstrapClass");

            // 根据类名创建对象
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.getDeclaredConstructor().newInstance();

            // 查找带有@InitMethod注解的方法并调用
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(InitMethod.class)) {
                    method.invoke(obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Properties loadProperties() throws IOException {
        // 读取属性文件
        Properties properties = new Properties();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("myapp.properties")) {
            properties.load(inputStream);
        }
        return properties;
    }
}


