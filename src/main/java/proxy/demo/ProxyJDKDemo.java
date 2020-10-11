package proxy.demo;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.*;

public class ProxyJDKDemo {

    interface IService {
        void sayHello();
    }
    static class RealService implements IService {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }
    static class SimpleInvocationHandler implements InvocationHandler {
        private Object realObj;
        public SimpleInvocationHandler(Object realObj) {
            this.realObj = realObj;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("entering " + method.getName());
            Object result = method.invoke(realObj, args);
            System.out.println("leaving " + method.getName());
            return result;
        }
    }
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException, IOException{
        IService realService = new RealService();
        IService proxyService = (IService) Proxy.newProxyInstance(
                IService.class.getClassLoader(),
                new Class<?>[] { IService.class },
                new SimpleInvocationHandler(realService));

//        Class<?> proxyCls = Proxy.getProxyClass(IService.class.getClassLoader(), new Class<?>[] { IService.class });
//        Constructor<?> ctor = proxyCls.getConstructor(new Class<?>[] { InvocationHandler.class });
//        InvocationHandler handler = new SimpleInvocationHandler(realService);
//        IService proxyService = (IService) ctor.newInstance(handler);

        proxyService.sayHello();

        ProxyJDKDemo.saveClass(proxyService.getClass());
    }

    public static void saveClass(Class cls) throws IOException{
        byte[]classFile = ProxyGenerator.generateProxyClass(cls.getName(), cls.getInterfaces());
        File file =new File(cls.getName() + ".class");
        FileOutputStream fos =new FileOutputStream(file);
        fos.write(classFile);
        fos.flush();
        fos.close();
    }
}
