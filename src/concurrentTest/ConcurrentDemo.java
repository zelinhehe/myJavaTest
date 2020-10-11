package concurrentTest;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 原子变量
public class ConcurrentDemo {
    private static AtomicInteger counter = new AtomicInteger(0);

    static class Visitor extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++)
                counter.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int num = 1000;
        Thread[] threads = new Thread[num];
        for (int i = 0; i < num; i++) {
            threads[i] = new Visitor();
            threads[i].start();
        }
        for (int i = 0; i < num; i++)
            threads[i].join();
        System.out.println(counter.get());
    }
}

/**
 * 使用死锁 tryLock避免死锁
 */
// 账户类
class Account {
    private Lock lock = new ReentrantLock();
    private volatile double money;
    public Account(double initialMoney) {
        this.money = initialMoney;
    }
    public void add(double money) {
        lock.lock();
        try {
            this.money += money;
        } finally {
            lock.unlock();
        }
    }
    public void reduce(double money) {
        lock.lock();
        try {
            this.money -= money;
        } finally {
            lock.unlock();
        }
    }

    public double getMoney() {
        return money;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public boolean tryLock() {
        return lock.tryLock();
    }
}

class AccountMgr {
    public static class NoEnoughMoneyException extends Exception {}

    // 转账的错误写法
    public static void transfer(Account from, Account to, double money)
            throws NoEnoughMoneyException {
        from.lock();
        try {
            to.lock();
            try {
                if (from.getMoney() > money) {
                    from.reduce(money);
                    to.add(money);
                } else {
                    throw new NoEnoughMoneyException();
                }
            } finally {
                to.unlock();
            }
        } finally {
            from.unlock();
        }
    }

    public static void transferTest() {
        final int accountNum = 10;
        final Account[] accounts = new Account[accountNum];
        final Random random = new Random();
        for (int i = 0; i < accountNum; i ++)
            accounts[i] = new Account(random.nextInt(10000));

        int threadNum = 100;
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread() {
                @Override
                public void run() {
                    int loopNum = 100;
                    for (int k = 0; k < loopNum; k++) {
                        int i = random.nextInt(accountNum);
                        int j = random.nextInt(accountNum);
                        int money = random.nextInt(10);
                        if (i != j) {
                            try {
//                                transfer(accounts[i], accounts[j], money);  // 死锁
                                tryTransfer(accounts[i], accounts[j], money);  // 不会死锁，成功返回true，失败返回false
                            } catch (NoEnoughMoneyException e) {
                            }
                        }
                    }
                }
            };
            threads[i].start();
        }
    }

    // 使用 tryLock尝试转账
    public static boolean tryTransfer(Account from, Account to, double money)
            throws NoEnoughMoneyException{
        if (from.tryLock()) {
            try {
                if (to.tryLock()) {
                    try {
                        if (from.getMoney() >= money) {
                            from.reduce(money);
                            to.add(money);
                        } else {
                            throw new NoEnoughMoneyException();
                        }
                        return true;
                    } finally {
                        to.unlock();
                    }
                }
            } finally {
                from.unlock();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        transferTest();
    }
}

class HashMapInfiniteLoop {
    public static void main(String[] args) {
//        final Map<Integer, Integer> map = new HashMap<>();
        final Map<Integer, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 1000; i++){
            Thread t = new Thread() {
                Random random = new Random();

                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        map.put(random.nextInt(), 1);
                    }
                }
            };
            t.start();
        }
    }
}

// ExecutorService
class ExecutorServiceTest {
    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("begin" + Thread.currentThread().getName());
            int sleepSeconds = new Random().nextInt(1000);
            Thread.sleep(1000 * 200);
            System.out.println("end" + Thread.currentThread().getName());
            return sleepSeconds;
        }
    }

    public static void main(String[] args) throws InterruptedException{
        List<Future<Integer>> list = new ArrayList<>();

        System.out.println("创建一个'执行服务'，提交一个任务 Task，让其异步执行");
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newScheduledThreadPool(5);
        Future<Integer> future1 = executorService.submit(new Task());
        list.add(future1);
        Future<Integer> future2 = executorService.submit(new Task());
        list.add(future2);

        System.out.println("执行其他任务");
        Thread.sleep(100);

        try {
            int times = 0;
            for (Future<Integer> f: list) {
                while (true) {
                    if (f.isDone()){
                        System.out.println("获取异步任务结果：" + f.get() + f.isDone());
                        break;
                    }
                    Thread.sleep(1000);
                    times++;
                    System.out.println("wait " + times);
                    if (times > 5) {
                        System.out.println("break");
                        break;
                    }
                }

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("关闭执行服务");
//        executorService.shutdown();
        executorService.shutdownNow();
    }
}

// ReentrantReadWriteLock
class MyCache {
    private Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = reentrantReadWriteLock.readLock();
    private Lock writeLock = reentrantReadWriteLock.writeLock();

    public Object get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }
    public Object write(String key, Object value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
    public void clear() {
        writeLock.lock();
        try {
            map.clear();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        myCache.write("a", "aaaaa");
        myCache.write("b", 12345);
        myCache.write("c", "ccccc");
        System.out.println(myCache.get("a"));
        System.out.println(myCache.get("b"));
        System.out.println(myCache.get("c"));
        System.out.println(myCache.get("d"));
        System.out.println(myCache.get("a"));
        myCache.clear();
        System.out.println(myCache.get("a"));
    }
}

// Semaphore
class AccessControlService {
    public static class ConcurrentLimitException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    private static final int MAX_PERMITS = 100;
    private Semaphore permits = new Semaphore(MAX_PERMITS, true);

    public boolean login(String name, String password) {
        if (!permits.tryAcquire()) {
            // 同时登录用户数超过限制
            throw new ConcurrentLimitException();
        }
        // 其他验证
        return true;
    }
    public void logout(String name) {
        permits.release();
    }
}

class RacerWithCountDownLatch {
    static class Racer extends Thread {
        CountDownLatch latch;

        public Racer(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                this.latch.await();
                System.out.println(getName() + " start run " + System.currentTimeMillis());
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int num = 10;
        CountDownLatch latch = new CountDownLatch(1);
        Thread[] racers = new Thread[num];
        for (int i = 0; i < num; i++) {
            racers[i] = new Racer(latch);
            racers[i].start();
        }
        Thread.sleep(1000 * 5);
        latch.countDown();
    }
}

class MasterWorkerDemo {
    static class Worker extends Thread {
        CountDownLatch latch;

        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                // 模拟执行任务
                Thread.sleep((int) (Math.random() * 1000));
                //􏲔􏲕􏲚􏲛􏲜􏲝 模拟异常情况
                if (Math.random() < 0.02) {
                    throw new RuntimeException("bad luck");
                }
            } catch (InterruptedException e) {
            } finally {
                this.latch.countDown();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            int workerNum = 100;
            CountDownLatch latch = new CountDownLatch(workerNum);
            Worker[] workers = new Worker[workerNum];
            for (int i = 0; i < workerNum; i++) {
                workers[i] = new Worker(latch);
                workers[i].start();
            }
            latch.await();
            System.out.println("collect worker results");
        }
    }
}

class CyclicBarrierDemo {
    static class Tourist extends Thread {
        CyclicBarrier barrier;

        public Tourist(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                // 模拟先各自独立运行
                Thread.sleep((int) (Math.random() * 1000));
                // 集合点 A
                barrier.await();
                System.out.println(this.getName() + " arrived A " + System.currentTimeMillis());
                Thread.sleep((int) (Math.random() * 1000));
                // 集合点 B
                barrier.await();
                System.out.println(this.getName() + " arrived B " + System.currentTimeMillis());
            } catch (InterruptedException e) {
            } catch (BrokenBarrierException e) {
            }
        }
    }

    public static void main(String[] args) {
        int num = 3;
        Tourist[] threads = new Tourist[num];
        CyclicBarrier barrier = new CyclicBarrier(num, new Runnable() {
            @Override
            public void run() {
                System.out.println("all arrived " + System.currentTimeMillis()
                        + " executed by " + Thread.currentThread().getName());
            }
        });
        for (int i = 0; i < num; i++) {
            threads[i] = new Tourist(barrier);
            threads[i].start();
        }
    }
}
