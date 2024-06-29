package locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionClass {
    private Lock lock = new ReentrantLock();
    private Condition conditionMet = lock.newCondition();

    public void method1(){
        lock.lock();
        try{
            System.out.println("Method1 before waiting for other thread's single");
            conditionMet.await();
            System.out.println("Method1 after waiting for other thread's single");
        }
        catch (InterruptedException e){
            throw new  RuntimeException();
        }
        finally {
            lock.unlock();
        }
    }

    public void method2(){
        lock.lock();
        try{

            System.out.println("Method2 before giving thread's single");
            Thread.sleep(3000);
            conditionMet.signalAll();
            System.out.println("Method2 after giving thread's single");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionClass conditionClass = new ConditionClass();
        Thread thread1= new Thread(()->{
            conditionClass.method1();
        });
        Thread thread2= new Thread(()->{
            conditionClass.method2();
        });

        thread1.start();
        thread2.start();

    }
}
