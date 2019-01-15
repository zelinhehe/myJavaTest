package proxy.demo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class SimpleCGLibDemo {

    static class RealService {
        public void sayHello() {
            System.out.println("hello");
        }
    }

    static class SimpleInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object object, Method method,
                                Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("entering " + method.getName());
            Object result = proxy.invokeSuper(object, args);
            System.out.println("leaving " + method.getName());
            return result;
        }
    }

    private static <T> T getProxy(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new SimpleInterceptor());
        return (T) enhancer.create();
    }

    public static void main(String[] args) {
        RealService proxyService = getProxy(RealService.class);
        proxyService.sayHello();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealService.class);
        enhancer.setCallback(new SimpleInterceptor());
        RealService proxyService2 = (RealService) enhancer.create();
        proxyService2.sayHello();
        try{
            saveClass(proxyService2.getClass());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveClass(Class cls) throws IOException {
        byte[]classFile = ProxyGenerator.generateProxyClass(cls.getName(), cls.getInterfaces());
        File file =new File(cls.getName() + ".class");
        FileOutputStream fos =new FileOutputStream(file);
        fos.write(classFile);
        fos.flush();
        fos.close();
    }
}
