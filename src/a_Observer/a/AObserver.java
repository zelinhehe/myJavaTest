package a_Observer.a;

public class AObserver implements Observer {
    private int value;
    private String name;

    public AObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(int value) {
        this.value = value;
        display();
    }

    public void display() {
        System.out.println(name + " receive value: " + value);
    }
}
