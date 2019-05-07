package a_Observer;

import java.util.ArrayList;

// 观察者接口
interface Observer {
    void update(int value);
}
// 主题接口
interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

// 观察者
class AObserver implements Observer {
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

// 主题
class ASubject implements Subject {
    private ArrayList<Observer> observers = new ArrayList<>();
    private int value = 0;

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(value);
        }
    }

    public void setValue(int value) {
        this.value = value;
        notifyObservers();
    }
}

public class Main {
    public static void main(String[] args) {
        // 新建两个观察者和一个主题
        AObserver aObserver1 = new AObserver("observer 1");
        AObserver aObserver2 = new AObserver("observer 2");
        ASubject aSubject = new ASubject();

        // 注册两个观察者
        aSubject.registerObserver(aObserver1);
        aSubject.registerObserver(aObserver2);

        // 主题更新，会自动通知已注册的观察者1和2
        aSubject.setValue(100);

        // 移出一个观察者
        aSubject.removeObserver(aObserver1);

        // 主题更新，会自动通知还在注册中的观察者2，已移除的观察者1没有通知
        aSubject.setValue(200);
    }
}
