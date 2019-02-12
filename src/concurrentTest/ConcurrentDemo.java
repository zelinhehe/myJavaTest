package concurrentTest;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
