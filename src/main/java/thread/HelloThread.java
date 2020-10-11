package main.java.thread;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

public class HelloThread extends Thread {
    @Override
    public void run() {
//        System.out.println("hello");
        System.out.println("main.java.thread name: " + Thread.currentThread().getName());
        System.out.println("main.java.thread name: " + Thread.currentThread().getId());
    }

    public static void main(String[] args) {
        Thread thread = new HelloThread();
        thread.start();
//        main.java.thread.run();
    }
}

class HelloRunable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread name: " + Thread.currentThread().getName());
        System.out.println("Thread name: " + Thread.currentThread().getId());
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new HelloRunable());
        thread.start();
    }
}

// 共享内存
class ShareMemoryDemo {
    private static int shared = 0;
    private static void incrShared() {
        shared++;
    }
    static class ChildThread extends Thread {
        private List<String> list;
        public ChildThread(List<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            incrShared();
            list.add(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException{
        List<String> list = new ArrayList<>();
        Thread thread1 = new ChildThread(list);
        Thread thread2 = new ChildThread(list);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(shared);
        System.out.println(list);
    }
}

// 竞态条件
class CounterThread extends Thread {
    private static int counter = 0;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++)
            counter++;
    }

    public static void main(String[] args) throws InterruptedException{
        int num = 1000;
        Thread[] threads = new Thread[num];
        for (int i = 0; i < num; i++){
            threads[i] = new CounterThread();
            threads[i].start();
        }
        for (int i = 0; i < num; i++)
            threads[i].join();
        System.out.println(counter);
    }
}

class VisibilityDemo {
    private static boolean shutdown = false;
    static class HelloThread extends Thread {
        @Override
        public void run() {
            while (!shutdown){

            }
            System.out.println("exit hello");
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread thread = new HelloThread();
//        main.java.thread.setDaemon(true);
        thread.start();
        Thread.sleep(1000);
        shutdown = true;
        System.out.println("exit main");
    }
}


/**
 * synchronized demo
  */
class Counter {
    private int count;
    public synchronized void incr() {
//    public void incr() {
        count++;
    }
    public synchronized int getCount() {
        return count;
    }
}

class CounterThread1 extends Thread {
    Counter counter;
    public CounterThread1(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++)
            counter.incr();
    }

    public static void main(String[] args) throws InterruptedException{
        int num = 1000;
        Counter counter = new Counter();
        Thread[] threads = new Thread[num];
        for (int i = 0; i < num; i++) {
            threads[i] = new CounterThread1(counter);
            threads[i].start();
        }
        for (int i = 0; i < num; i++)
            threads[i].join();
        System.out.println(counter.getCount());
    }
}

class DeadLockDemo {
    private static Object lockA = new Object();
    private static Object lockB = new Object();

    private static void startThreadA() {
        Thread aThread = new Thread() {
            @Override
            public void run() {
                System.out.println("a 申请锁 A...");
                synchronized (lockA) {
                    System.out.println("a 持有锁 A");
                    try { Thread.sleep(1000); } catch (InterruptedException e) { }
                    System.out.println("a 申请锁 B...");

                    synchronized (lockB) {  // 申请锁B
                        System.out.println("a 持有锁 B");
                    }
                    System.out.println("a 释放锁 B");
                }
                System.out.println("a 释放锁 A");
            }
        };
        aThread.start();
    }

    private static void startThreadB() {
        Thread bThread = new Thread() {
            @Override
            public void run() {
                System.out.println("b 申请锁 B...");
                synchronized (lockB) {
                    System.out.println("b 持有锁 B");
                    try { Thread.sleep(1000); } catch (InterruptedException e) { }
                    System.out.println("b 申请锁 A...");
                    synchronized (lockA) {
                        System.out.println("b 持有锁 A");
                    }
                }
                System.out.println("b 释放锁 B");
            }
        };
        bThread.start();
    }

    public static void main(String[] args) {
        startThreadA();
        startThreadB();
    }
}

// wait/notify demo
class WaitThread extends Thread {
    private volatile boolean fire = false;

    @Override
    public void run() {
        try {
            synchronized (this) {
                System.out.println("run");
//                while (!fire)
                    wait();
            }
            System.out.println("fired");
        } catch (InterruptedException e) {
        }
    }

    public synchronized void fire() {
//        this.fire = true;
        notify();
    }

    public static void main(String[] args) throws InterruptedException{
        WaitThread waitThread = new WaitThread();
        waitThread.start();
        Thread.sleep(5000);
        System.out.println("fire");
        waitThread.fire();
    }
}

/**
 * producer-consumer demo
  */
class MyBlockingQueue<E> {
    private Queue<E> queue = null;
    private int limit;

    public MyBlockingQueue(int limit) {
        this.limit = limit;
        queue = new ArrayDeque<>(limit);
    }

    public synchronized void put(E e) throws InterruptedException {
        while (queue.size() == limit) {
            System.out.println("-------- empty put wait");
            wait();
        }
        queue.add(e);
//        System.out.println("-------- put notifyAll");
        notifyAll();
    }

    public synchronized E take() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("-------- empty take wait");
            wait();
        }
        E e = queue.poll();
        notifyAll();
//        System.out.println("-------- take notifyAll");
        return e;
    }
}

class Producer extends Thread {
    MyBlockingQueue<String> queue = null;
    public Producer(MyBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (true) {
                String task = String.valueOf(num);
                queue.put(task);
                System.out.println("producer task " + task);
                num++;
                Thread.sleep((int)(Math.random() * 1000));
            }
        } catch (InterruptedException e) {
        }
    }
}

class Consumer extends Thread {
    MyBlockingQueue<String> queue;
    public Consumer(MyBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String task = queue.take();
                System.out.println("handle task " + task);
                Thread.sleep((int)(Math.random() * 900));
            }
        } catch (InterruptedException e) {
        }
    }
}

class ProducerConsumerTest {
    public static void main(String[] args) {
        MyBlockingQueue<String> queue = new MyBlockingQueue<>(3);
        new Producer(queue).start();
        new Consumer(queue).start();
    }
}

/**
 * 同时开始
 */
class FireFlag {  // 协作对象
    private volatile boolean fired = false;
    public synchronized void waitForFire(String name) throws InterruptedException {
        while (!fired) {
            System.out.println(name + " is waiting...");
            wait();
        }
    }

    public synchronized void fire() {
        this.fired = true;
        notifyAll();
    }
}

class Racer extends Thread {
    FireFlag fireFlag;
    public Racer(FireFlag fireFlag) {
        this.fireFlag = fireFlag;
    }

    @Override
    public void run() {
        try {
            this.fireFlag.waitForFire(Thread.currentThread().getName());
            System.out.println("start run " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
        }
    }
}

class RacerDemo {
    public static void main(String[] args) throws InterruptedException{
        int num = 10;
        FireFlag fireFlag = new FireFlag();
        Thread[] racers = new Thread[num];
        for (int i = 0; i < num; i++) {
            racers[i] = new Racer(fireFlag);
            racers[i].start();
        }
        Thread.sleep(2000);
        fireFlag.fire();
    }
}

/**
 * 等待结束
 */

class MyLatch {  // 协作对象
    private int count;
    public MyLatch(int count) {
        this.count = count;
    }

    public synchronized void await() throws InterruptedException {
        while (count > 0)
            wait();
    }

    public synchronized void countDown() {
        count--;
        System.out.println("    count: " + count);
        if(count <= 0)
            notifyAll();
    }
}

class Worker extends Thread {  // 工作子线程，使用 MyLatch
    MyLatch latch;
    public Worker(MyLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((int)(Math.random() * 1000));  // simulate working on task
            System.out.println(Thread.currentThread().getName() + " countDown");
            this.latch.countDown();  // 本线程工作完成，计数减一
        } catch (InterruptedException e) {
        }
    }
}

class MasterWorker {

    public static void main(String[] args) throws InterruptedException{
        int workNum = 10;
        MyLatch myLatch = new MyLatch(workNum);
        Worker[] workers = new Worker[workNum];
        for (int i = 0; i < workNum; i++) {
            workers[i] = new Worker(myLatch);
            workers[i].start();
        }
        System.out.println("master worker await...");
        myLatch.await();
        System.out.println("collect worker results");
    }
}

/**
 * 异步结果
 */

class ExecuteThread<V> extends Thread {
    private V result = null;
    private Exception exception = null;
    private boolean done = false;
    private Callable<V> task;
    private Object lock;

    public ExecuteThread(Callable<V> task, Object lock) {
        this.task = task;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            result = task.call();
        } catch (Exception e) {
            exception = e;
        } finally {
            synchronized (lock) {
                done = true;
                lock.notifyAll();  // 唤醒可能在等待的主线程
            }
        }
    }

    public V getResult() {
        return result;
    }

    public boolean isDone() {
        return done;
    }

    public Exception getException() {
        return exception;
    }
}

interface MyFuture <V> {
    V get() throws Exception;
}

class MyExecutor {

    public <V> MyFuture<V> execute(final Callable<V> task) {
        final Object lock = new Object();
        final ExecuteThread<V> thread = new ExecuteThread<>(task, lock);
        thread.start();

        MyFuture<V> myFuture = new MyFuture<V>() {
            @Override
            public V get() throws Exception {
                synchronized (lock) {
                    while (!thread.isDone()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    if (thread.getException() != null)
                        throw thread.getException();
                    return thread.getResult();
                }
            }
        };
        return myFuture;
    }
}

class MyExecutorTest {
    public static void main(String[] args) {
        MyExecutor myExecutor = new MyExecutor();
        // 子任务
        Callable<Integer> subTask = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("执行异步任务...");
                int millis = (int) (Math.random() * 1000);  // ...执行异步任务
                Thread.sleep(millis);
                return millis;
            }
        };

        System.out.println("异步调用...");
        MyFuture<Integer> myFuture = myExecutor.execute(subTask);  // 异步调用，返回一个 MyFuture对象

        System.out.println("主线程执行其他操作...");// ...执行其他操作

        System.out.println("获取异步调用的结果...");
        try {
            Integer result = myFuture.get();  // 获取异步调用的结果
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * 集合点
 */

class AssemblePoint {
    private int n;
    public AssemblePoint(int n) {
        this.n = n;
    }
    public synchronized void await() throws InterruptedException {
        if (n > 0) {
            n--;
            if (n == 0)
                notifyAll();
            else {
                while (n != 0)
                    wait();
            }
        }
    }
}

class AssemblePointTest {
    static class Tourist extends Thread {
        AssemblePoint assemblePoint;
        public Tourist(AssemblePoint assemblePoint) {
            this.assemblePoint = assemblePoint;
        }

        @Override
        public void run() {
            try {
                // 模拟先各自执行
                System.out.println("各自执行..." + Thread.currentThread().getName());
                Thread.sleep((int)(Math.random() * 1000));
                // 集合
                System.out.println("执行完毕，集合" + Thread.currentThread().getName());
                assemblePoint.await();
                System.out.println("arrived 集合完毕");
                System.out.println("集合后，执行其他操作");
            } catch (InterruptedException e) {
            }
        }
    }
    public static void main(String[] args) {
        int num = 10;
        Tourist[] threads = new Tourist[num];
        AssemblePoint assemblePoint = new AssemblePoint(num);
        for (int i = 0; i < num; i++) {
            threads[i] = new Tourist(assemblePoint);
            threads[i].start();
        }
    }
}
