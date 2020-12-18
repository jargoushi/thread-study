package synchronizedDemo;

/**
 * 一个同步方法调用另外一个同步方法
 * 可以, 因为synchronized是可重入锁
 * 一个线程已经拥有某个对象的锁, 再次申请的时候仍然会获取到该对象的锁
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/17 11:18
 * @description
 */
public class Demo5 {

    private synchronized void m1() {

        System.out.println("m1");
        m2();
    }

    private synchronized void m2() {

        System.out.println("m2");
    }

    public static void main(String[] args) {

        new Thread(() -> new Demo5().m1()).start();
    }
}
