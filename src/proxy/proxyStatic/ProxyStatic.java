package proxy.proxyStatic;

public class ProxyStatic extends Hello{
    private Hello hello = new Hello();

    public void sayHello(){
        System.out.println("START log");
        hello.sayHello();
        System.out.println("END log");
    }
}
