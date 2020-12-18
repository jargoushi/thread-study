package synchronizedDemo;

import java.util.concurrent.TimeUnit;

/**
 * synchronized 锁的是对象, 堆内存中每个对象头都有个monitor来标识锁
 *
 * 如果对象的属性发生了变化, 不影响锁的使用. 因为堆内存的对象地址没有发生变更
 * 如果对象发生了变化, 影响锁的使用. 因为堆内存的对象地址发生了变更
 *
 * 应该避免将锁定对象的引用变成另外的对象
 * 所以可以将锁对象设置为final 防止被更改
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/18 14:02
 * @description
 */
public class Demo8 {

    /*final */Object lock = new Object();

    void m() {
        synchronized (lock) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {


        Demo8 t = new Demo8();

        new Thread(() -> t.m(), "t1").start();

        // 休眠3秒, 让t1先执行1会
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 把锁变了, 内存地址发生了变化. 如果去掉这句代码, 那么t2将永远无法执行
        t.lock = new Object();

        // 第二个线程获取到了锁, 但是这把锁已经不是之前的那把锁了
        new Thread(() -> t.m(), "t2").start();
    }
}
