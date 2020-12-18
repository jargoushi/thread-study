package synchronizedDemo;

/**
 * TODO
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/17 9:39
 * @description
 */
public class Demo1 implements Runnable {

    private int count = 10;

    /**
     * 不加互斥锁, 多个线程可以同时执行run方法, 会造成结果不正确
     * 加上synchronized同步锁, 多个线程竞争, 只有一个线程能获取到锁, 执行run方法, 其他线程进入等待队列. 等之前线程释放了锁, 等待队列中的线程再争抢锁
     */
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + "," + count);
    }

    public static void main(String[] args) {

        Demo1 t = new Demo1();
        for (int i = 0; i < 10; i++) {
            new Thread(t, "THREAD" + i).start();
        }
    }
}
