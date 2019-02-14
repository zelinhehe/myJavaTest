package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 注解的应用：定制序列化
 */
public class AnnotationTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Student zhangsan = new Student("张三", simpleDateFormat.parse("1990-12-12"));
        System.out.println(SimpleFormatter.format(zhangsan));
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Label {
    String value() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Format {
    String pattern() default "yyyy-MM-dd HH:mm:ss";
    String timezone() default "GMT+8";
}

class Student {
    @Label("姓名")
    private String name;
    @Label("日期")
    @Format(pattern = "yyyy/MM/dd")
    private Date born;

    public Student(String name, Date born) {
        this.name = name;
        this.born = born;
    }
}

class SimpleFormatter {

    private static Object formatDate(Field f, Object value) {
        Format format = f.getAnnotation(Format.class);
        if (format != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format.pattern());
            sdf.setTimeZone(TimeZone.getTimeZone(format.timezone()));
            return sdf.format(value);
        }
        return value;
    }

    public static String format(Object obj) {
        try {
            Class<?> cls = obj.getClass();
            StringBuilder sb = new StringBuilder();
            for (Field f : cls.getDeclaredFields()) {
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                Label label = f.getAnnotation(Label.class);
                String name = label != null ? label.value() : f.getName();
                Object value = f.get(obj);
                if (value != null && f.getType() == Date.class) {
                    value = formatDate(f, value);
                }
                sb.append(name + "：" + value + "\n");
            }
            return sb.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

/**
 * 注解的应用：DI容器
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface SimpleInject {
}

class ServiceA {
    @SimpleInject
    ServiceB b;

    public void callB() {
        b.action();
    }
}
class ServiceB {
    public void action() {
        System.out.println("I'm B.");
    }
}

class SimpleContainer {
    public static <T> T getInstance(Class<T> cls) {
        try {
            T obj = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(SimpleInject.class)) {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    Class<?> fieldCls = f.getType();
                    f.set(obj, getInstance(fieldCls));
                }
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class DITest {
    public static void main(String[] args) {
        ServiceA serviceA = SimpleContainer.getInstance(ServiceA.class);
        serviceA.callB();
    }
}
