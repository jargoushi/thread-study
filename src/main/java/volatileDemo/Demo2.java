package volatileDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile只是保证了变量的可见性, 但是保证不了原子性
 *
 * 加上volatile变量后发现结果小于等于100000
 * 因为count++, 分为三步 1. 获取count值 2. count自增 3. 重新赋值给count
 *
 * 当多个线程执行count++时, 可能造成
 * 可能存在多个线程同时从主内存读取到工作内存都为0. 其中一个线程执行完自增还没有写入到主内存时, 另外一个线程已经写入主内存为1.
 * 那么再写入主内存1会造成多个线程覆盖
 *
 * 需要加上synchronized互斥锁或者使用CAS操作
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/18 13:48
 * @description
 */
public class Demo2 {

    // 方案1, 加上synchronized
    /*volatile */int count = 0;

    // 方案2, 使用CAS操作
    AtomicInteger casCount = new AtomicInteger(0);
    /*synchronized*/ void m() {
        for (int i = 0; i < 10000; i++) {
//            count++;
            casCount.incrementAndGet();
        }
    }

    public static void main(String[] args) {

        Demo2 t = new Demo2();

        List<Thread> threads = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(() -> t.m(), "thread" + i));
        }

        threads.forEach(thread -> {
            thread.start();
        });

        // 插队, 等所有的线程执行完成后再执行主线程
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.casCount.get());
    }
}
