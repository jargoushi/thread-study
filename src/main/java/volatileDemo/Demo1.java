package volatileDemo;

/**
 * volatile 保证了变量的可见性
 *
 * JMM(java memory model) java内存模型规定所有的变量都存储在主内存中, 每条线程还有自己的工作内存.
 * 工作内存中保存了该变量主内存的副本拷贝, 线程对变量的所有操作(读取, 赋值)都必须在工作内存上进行, 不能直接读写主内存的变量
 * 不同线程之间无法直接访问其他现场工作内存的变量, 线程间变量的传递需要在主内存完成
 *
 * 不加volatile的情况下
 *  t线程先执行, 从主内存中将running读取到工作内存(值为true)
 *  主线程后执行,从主内存中将running读取到工作内存(值为true)改为false, 并写入到主内存
 *  但是t线程不会从主内存中获取, 所以会一直循环, 不会输出"m end"
 *
 *  而加上volatile的情况下
 *  当主线程将running修改为false并写入到主内存的时候, 主内存中加了volatile的变量发生了改变的时候,
 *  会通知其他的线程重新从主内存获取, 此时t线程就更新工作内存的running为false了, 结束循环输出"m end"
 *
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/17 15:57
 * @description
 */
public class Demo1 {

    volatile boolean running = true;

    private void m() {

        System.out.println("m start");
        while (running) {

        }

        System.out.println("m end");
    }

    public static void main(String[] args) {

        Demo1 t = new Demo1();
        new Thread(() -> t.m()).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running = false;
    }
}
