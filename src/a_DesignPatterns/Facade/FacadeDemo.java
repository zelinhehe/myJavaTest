package a_DesignPatterns.Facade;

class App1 { public void start() { System.out.println("App1 start"); }}
class App2 { public void on() { System.out.println("App2 on"); }}
class App3 { public void run() { System.out.println("App2 run"); }}

class Facade {
    public void play1() {
        App1 app1 = new App1();
        App2 app2 = new App2();
        App3 app3 = new App3();
        app1.start();
        app2.on();
        app3.run();
    }
    public void play2() {
        App1 app1 = new App1();
        App3 app3 = new App3();
        app1.start();
        app3.run();
    }
}
public class FacadeDemo {

    public static void main(String[] args) {
        // --- 不使用 Facade ---
        App1 app1 = new App1();
        App2 app2 = new App2();
        App3 app3 = new App3();
        // 启动系统模式play1
        app1.start();
        app2.on();
        app3.run();
        // 启动系统模式play2
        app1.start();
        app3.run();
        // --- 使用 Facade ---
        Facade facade = new Facade();
        // 一键启动系统模式play1
        facade.play1();
        // 一键启动系统模式play2
        facade.play2();
    }
}
