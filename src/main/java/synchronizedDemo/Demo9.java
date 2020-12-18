package synchronizedDemo;

import java.util.concurrent.TimeUnit;

/**
 * m1和m2其实锁定的是同一个对象, t2线程得不到执行机会
 *
 * 更坑的情况可能是, 比如使用了一个类库, 刚好这个类库有段代码使用了hello作为锁, 那么有可能你的代码永远也无法执行. 因为你和jar包使用了同一把锁
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/18 14:24
 * @description
 */
public class Demo9 {

    String s1 = "hello";

    String s2 = "hello";

    void m1() {
        synchronized (s1) {
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

    void m2() {
        synchronized (s2) {
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

        Demo9 t = new Demo9();
        new Thread(() -> t.m1(), "t1").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> t.m2(), "t2").start();

    }
}
