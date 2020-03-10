package DesignPattern.Adapter;

interface Duck {
    void quack();
    void fly();
}
interface Turkey {
    void gobble();
    void fly();
}
class ADuck implements Duck {
    public void quack() { System.out.println("A duck quack"); }
    public void fly() { System.out.println("A duck fly"); }
}
class ATurkey implements Turkey {
    public void gobble() { System.out.println("A turkey gobble"); }
    public void fly() { System.out.println("A turkey fly"); }
}
class TurkeyAdapter implements Duck {
    Turkey turkey;
    public TurkeyAdapter(Turkey turkey) {this.turkey = turkey;}
    public void quack() { turkey.gobble(); }
    public void fly() {
        for (int i = 0; i < 5; i++)
            turkey.fly();
    }
}
public class AdapterDemo {
    public static void main(String[] args) {
        ADuck aDuck = new ADuck();
        ATurkey aTurkey = new ATurkey();
        // 把turkey适配成duck
        Duck turkeyAdapter = new TurkeyAdapter(aTurkey);

        System.out.println("turkey:");
        aTurkey.gobble();
        aTurkey.fly();

        System.out.println("duck:");
        testDuck(aDuck);

        System.out.println("turkey adapter:");
        testDuck(turkeyAdapter);
    }
    private static void testDuck(Duck duck) {
        duck.quack();
        duck.fly();
    }
}
