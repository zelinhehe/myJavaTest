package lambdaDeal;

import java.util.function.IntConsumer;

public class lambdaDeal {
    public static void main(String[] args){
        repeat(3, () -> System.out.println("Hello."));
        repeat(3, i -> System.out.println("Count: " + i));
        repeatCustom(3, i -> System.out.println("Count: " + i));
    }

    public static void repeat(int n, Runnable action){
        for (int i = 0; i < n; i++)
            action.run();
    }

    public static void repeat(int n, IntConsumer action) {
        for (int i = 0; i < n; i++)
            action.accept(i);  // System.out.println("Countdown: " + (9 - i));
    }

    public static void repeatCustom(int n, Custom action){
        for (int i = 0; i < n; i++)
            action.a(i);
    }
}

@FunctionalInterface
interface Custom{
    void a(int value);
}
