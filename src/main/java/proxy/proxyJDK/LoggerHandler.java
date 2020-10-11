package proxy.proxyJDK;

import java.lang.reflect.*;

public class LoggerHandler implements InvocationHandler {

    private Object target;

    public LoggerHandler(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy,
                         Method method,
                         Object[] args) throws Throwable{
        System.out.println("START log");
        Object result = method.invoke(target, args);
        System.out.println("END log");
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(){
        return (T)Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }
}
