package proxy.proxyCGlib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

public class LogInterceptor implements MethodInterceptor {

    public Object intercept(Object obj,
                            Method method,
                            Object[] args,
                            MethodProxy proxy) throws Throwable{
        System.out.println("START log");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("END log");
        return result;
    }
}
