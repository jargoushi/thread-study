package synchronizedDemo;

/**
 * 程序执行过程中, 如果出现了异常, 锁会释放
 *
 * 第一个线程将count自增到5时, 抛出异常. 第二个线程可以获取到锁执行m方法
 *
 * 手动catch异常则不会释放锁
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/17 14:16
 * @description
 */
public class Demo7 {

    int count = 0;

    synchronized void m() {
        System.out.println(Thread.currentThread().getName() + " start");
        while (true) {
            count++;
            System.out.println(Thread.currentThread().getName() + "    " + count);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 5) {
                int i = 1 / 0;
            }
        }
    }

    public static void main(String[] args) {
        Demo7 t = new Demo7();
        new Thread(() -> t.m()).start();

        new Thread(() -> t.m()).start();
    }
}
