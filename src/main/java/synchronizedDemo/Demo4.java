package synchronizedDemo;

/**
 * 对写方法加锁, 对读方法不加锁
 * 会出现脏读的问题
 * 尽管对写进行了加锁, 但是由于没有对读加锁, 那么有可能读到还没有写完成的数据, 产生脏读问题
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/17 11:08
 * @description
 */
public class Demo4 {


    private double balance;

    public synchronized void set(double balance) {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public double get() {
        return balance;
    }

    public static void main(String[] args) {

        Demo4 demo4 = new Demo4();

        new Thread(() -> demo4.set(10.0F)).start();

        System.out.println(demo4.get());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(demo4.get());
    }
}
