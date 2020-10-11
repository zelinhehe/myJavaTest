public class A {
    public static void main(String[] args) {
        // 对Synchronized Class对象进行加锁
        synchronized (A.class) {
        }
        // 静态同步方法，对Synchronized Class对象进行加锁
        m();
    }

    public static synchronized void m() {
    }
}
