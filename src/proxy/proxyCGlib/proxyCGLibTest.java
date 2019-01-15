package proxy.proxyCGlib;

import net.sf.cglib.proxy.Enhancer;

public class proxyCGLibTest {
    public static void main(String[] args){
        // not use proxy.proxyCoreJava
        Hello hello = new Hello();
        hello.sayHello();

        // use proxy.proxyCoreJava by CGLib
        Enhancer enhancer = new Enhancer(); // 创建一个增强器，用来在运行时生成类
        enhancer.setSuperclass(Hello.class); // 设置要继承的目标类
        enhancer.setCallback(new LogInterceptor()); // 设置 logInterceptor
        Hello helloNew = (Hello) enhancer.create(); // 生成新的类
        helloNew.sayHello(); // 调用代理类的方法
    }
}
