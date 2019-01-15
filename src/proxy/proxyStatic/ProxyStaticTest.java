package proxy.proxyStatic;

public class ProxyStaticTest {

    public static void main(String[] args){

        Hello hello = new Hello();
        hello.sayHello();

        ProxyStatic helloNew = new ProxyStatic();
        helloNew.sayHello();
    }
}
