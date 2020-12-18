package 练习题;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器, 实现两个方法 add 和 size
 * 写两个线程, 线程1添加10个元素到容器中, 线程2监控元素的个数, 当个数达到5时, 线程2提示并结束
 *
 * 使用wait和notify, 生产消费者模型
 * wait: 让正在运行的线程进入等待状态, 并释放锁
 * notify: 唤醒某个正在等待的线程, 不能精确到哪个线程
 * notify: 唤醒所有正在等待的线程
 * wait会释放锁, notify不会释放锁
 *
 * t2为什么要等到t1执行完才能继续执行
 *
 * 首先t2线程执行, 判断size不等于5, t2线程等待释放锁.
 * t1线程强到锁, 当size为5时, 唤醒t2线程但是并未释放锁
 * 那么t2线程是执行不了的, 只能等待t1线程执行完线程体释放了锁资源才能执行
 *
 * 要如何解决呢, 也就是t1线程中size等于5时, 不仅要notify唤醒t2线程并且要wait释放锁暂停t1线程
 * 等到t2线程执行完后再notify唤醒t1线程
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/18 15:32
 * @description
 */
public class MyContainer3 {

    volatile List container = new ArrayList();

    private void add(Object o) {
        container.add(o);
    }

    private int size() {
        return container.size();
    }

    public static void main(String[] args) {

        MyContainer3 container = new MyContainer3();
        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 start");
                if (container.size() != 5) {
                    // 当size不等于5时停止当前线程, 并释放锁让t1线程执行
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 end");

                // 通知t1继续
                lock.notify();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t1 start");
                for (int i = 0; i < 10; i++) {
                    container.add(new Object());
                    if (container.size() == 5) {
                        lock.notify();

                        // 必须要wait, 否则即使notify唤醒了t1线程也是没有用滴, 因为notify并没有释放锁, 需要wait释放锁资源
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    // 停顿一下, 让t1线程执行
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t1 end");
            }
        }, "t1").start();
    }
}
