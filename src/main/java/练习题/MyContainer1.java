package 练习题;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器, 实现两个方法 add 和 size
 * 写两个线程, 线程1添加10个元素到容器中, 线程2监控元素的个数, 当个数达到5时, 线程2提示并结束
 *
 * 必须要为container添加volatile关键字
 * 给list添加volatile关键字后, t2线程才能感知到list size的变化, 否则t2感知到的size一直为0
 *
 * 但是仍然存在两个问题:
 * 1. t2线程的死循环很耗费CPU, 如果不使用死循环应该怎么处理?
 * 2. 由于没加同步, 假如size等于5时, 另外一个线程又加了1次为6, 那么实际上t2线程等到size为6时才break
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/18 15:11
 * @description
 */
public class MyContainer1 {

    volatile List container = new ArrayList();

    private void add(Object o) {
        container.add(o);
    }

    private int size() {
        return container.size();
    }

    public static void main(String[] args) {

        MyContainer1 container = new MyContainer1();

        new Thread(() -> {

            for (int i = 0; i < 10; i++) {
                container.add(new Object());
                System.out.println("add" + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {

            while (true) {
                if (container.size() == 5) {
                    break;
                }
            }
            System.out.println("t2 结束");
        }, "t2").start();
    }
}
