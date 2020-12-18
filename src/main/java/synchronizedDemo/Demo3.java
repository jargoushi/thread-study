package synchronizedDemo;

/**
 * 同步方法和非同步方法可以同时调用嘛?
 *
 * 可以同时调用, 因为同步方法需要获取锁, 而非同步方法压根就不需要获取锁
 *
 * 就比如一个厕所
 * 如果只有一扇门, 那么所有人想进入厕所必须尝试获取锁, 且只有获取到锁的可以进入(同步方法)
 * 如果有一扇门一个窗户, 那么当门上锁时, 其他人可以通过窗户爬进来不需要获取锁, 当然可以执行了(非同步方法)
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/17 9:46
 * @description
 */
public class Demo3 {

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + "m1 start...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m1 end....");
    }

    public void m2() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m2");
    }

    public static void main(String[] args) {
        Demo3 t = new Demo3();

        new Thread(() -> t.m1(), "t1").start();
        new Thread(() -> t.m2(), "t2").start();

    }
}
