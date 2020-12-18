package 练习题;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 使用countDownLatch的await 和 countDown方法代替wait notify来进行通知
 *
 * CountDownLatch不涉及锁定, 当count的值为0时当前线程继续执行
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/18 16:02
 * @description
 */
public class MyContainer4 {

    volatile List container = new ArrayList();

    private void add(Object o) {
        container.add(o);
    }

    private int size() {
        return container.size();
    }

    public static void main(String[] args) {

        MyContainer4 container = new MyContainer4();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("t2 start");
            if (container.size() != 5) {
                // 当size不等于5时阻塞当前线程
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2 end");
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 start");
            for (int i = 0; i < 10; i++) {
                container.add(new Object());
                if (container.size() == 5) {
                    // 打开门闩, 让t2线程运行
                    latch.countDown();
                }

                // 停顿一下, 让t1线程执行
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t1 end");
        }, "t1").start();
    }
}
