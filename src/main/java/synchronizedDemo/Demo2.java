package synchronizedDemo;

/**
 * TODO
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/17 9:44
 * @description
 */
public class Demo2 implements Runnable{

    private int count = 10;

    /**
     * 启动了10个线程, 每个线程都有一个count变量, 无需加锁
     */
    public void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + "," + count);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            Demo2 t = new Demo2();
            new Thread(t, "THREAD" + i).start();
        }
    }
}
