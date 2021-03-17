import java.util.concurrent.*;

public class AsyncTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        f();
//        f1();
//        f2();
    }

    public static void f() throws ExecutionException, InterruptedException {
        Future<Integer> future = Executors.newCachedThreadPool().submit(() -> 1 + 2);
        System.out.println(future.get());
    }

    public static void f1() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1 + 2);
        Executors.newCachedThreadPool().submit(futureTask);
//        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }

    public static void f2() throws ExecutionException, InterruptedException {
//        CompletableFuture<Void> f1 = CompletableFuture.runAsync(()-> System.out.println("1"));
//        System.out.println(f1.get());
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 1 + 2);
        System.out.println(f2.get());
    }
}
