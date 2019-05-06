package a_Observer.a;

import java.util.ArrayList;

public class ASubject implements Subject {
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
