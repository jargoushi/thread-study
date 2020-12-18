package synchronizedDemo;

/**
 * 子类的同步方法调用父类的同步方法
 * synchronized是可重复锁, 继承也是可以的
 * @author ruwenbo
 * @version 1.0
 * @date 2020/12/17 11:25
 * @description
 */
public class Demo6 extends Parent {

    @Override
    synchronized void m() {
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");
    }

    public static void main(String[] args) {

        new Demo6().m();
    }
}

class Parent {

    synchronized void m() {
        System.out.println("parent m start");
        System.out.println("parent m end");
    }
}
