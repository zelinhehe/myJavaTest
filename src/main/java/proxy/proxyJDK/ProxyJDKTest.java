package proxy.proxyJDK;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class ProxyJDKTest {

    public static void main(String[] args) throws IOException {

        IHello hello = new Hello();

        // not use proxy
        hello.sayHello();

        // use proxy by JDK
        LoggerHandler handler = new LoggerHandler(hello);
        IHello helloNew = (IHello) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(), // hello.getClass().getClassLoader(),
                hello.getClass().getInterfaces(),
                handler
        );
//        IHello helloNew = handler.getProxy();
        helloNew.sayHello();

        ProxyJDKTest.saveClass(helloNew.getClass());
    }

    public static void saveClass(Class cls) throws IOException {
        byte[] classFile = ProxyGenerator.generateProxyClass(cls.getName(), cls.getInterfaces());
        File file =new File(cls.getName() + ".class");
        FileOutputStream fos =new FileOutputStream(file);
        fos.write(classFile);
        fos.flush();
        fos.close();
    }
}


