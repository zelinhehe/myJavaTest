import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Test {

    // 订单队列
    Vector<String> pos = new Vector<>();
    // 派送单队列
    Vector<String> dos = new Vector<>();

    // 执行回调的线程池
    Executor executor = Executors.newFixedThreadPool(1);

    final CyclicBarrier barrier = new CyclicBarrier(2, ()-> executor.execute(this::check));

    void check(){
        // 对账
        System.out.println(pos.remove(0) + "--" + dos.remove(0));
    }

    void checkAll(){
        // 循环查询订单库
        Thread T1 = new Thread(()->{
            while(true){
                // 查询订单库
                pos.add("pos");
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        T1.start();

        // 循环查询运单库
        Thread T2 = new Thread(()->{
            while(true){
                // 查询运单库
                dos.add("dos");
                try {
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        T2.start();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.checkAll();
    }
}
