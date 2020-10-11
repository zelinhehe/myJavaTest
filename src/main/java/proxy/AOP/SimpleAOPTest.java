package proxy.AOP;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;

class ServiceA {
    @SimpleInject
    ServiceB b;

    public void callB(){
        b.action();
    }
    public ServiceB getB() {
        return b;
    }
}

class ServiceB {

    public void action(){
        System.out.println("I'm B");
    }
}

public class SimpleAOPTest {
    public static void main(String[] args) {
        ServiceA serviceA = CGLibContainer.getInstance(ServiceA.class);
        serviceA.callB();
    }
}

/**
 * 注解
 */
@Retention(RUNTIME)
@Target(FIELD)
@interface SimpleInject {
}

@Retention(RUNTIME)
@Target(TYPE)
@interface Aspect {
    Class<?>[] value();
}

/**
 * 切面类
 */
@Aspect({ ServiceA.class, ServiceB.class })
class ServiceLogAspect {

    public static void before(Object object, Method method, Object[] args) {
        System.out.println("entering " + method.getDeclaringClass().getSimpleName() + "::" + method.getName() + ", args: " + Arrays.toString(args));
    }
    public static void after(Object object, Method method, Object[] args, Object result) {
        System.out.println("leaving " + method.getDeclaringClass().getSimpleName() + "::" + method.getName() + ", result: " + result);
    }
}

@Aspect({ ServiceB.class })
class ExceptionAspect {
    public static void exception(Object object, Method method, Object[] args, Throwable e) {
        System.err.println("exception when calling: " + method.getName() + "," + Arrays.toString(args));
    }
}

