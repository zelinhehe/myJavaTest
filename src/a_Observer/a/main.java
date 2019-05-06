package a_Observer.a;

public class main {
    public static void main(String[] args) {
        AObserver aObserver1 = new AObserver("observer 1");
        AObserver aObserver2 = new AObserver("observer 2");
        ASubject aSubject = new ASubject();

        aSubject.registerObserver(aObserver1);
        aSubject.registerObserver(aObserver2);

        aSubject.setValue(100);

        aSubject.removeObserver(aObserver1);

        aSubject.setValue(200);
    }
}
