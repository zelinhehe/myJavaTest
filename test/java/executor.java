import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class executor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {

            Future<Object> future = executorService.submit(() -> {
                throw new RuntimeException("submit");
            });
            future.get();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            executorService.execute(() -> {
            throw new RuntimeException("execute");
        });
        } catch (Exception e) {
            System.out.println(e);
        }

        executorService.shutdown();
    }
}
