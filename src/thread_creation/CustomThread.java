package thread_creation;

import java.util.concurrent.TimeUnit;

public class CustomThread extends Thread{
    @Override
    public void run(){
        for (int i = 1; i <=10 ; i++) {
            System.out.print(i*5+" ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        Thread customThread = new CustomThread();
        customThread.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("Manish");
            try {
                TimeUnit.SECONDS.sleep(1);

            }
            catch (InterruptedException exception){
                exception.printStackTrace();
            }
        }

        Runnable myRunnable=()->{
            System.out.println("Chauhan");
            try{
                TimeUnit.MICROSECONDS.sleep(750);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        };

        Thread runnableThread = new Thread(myRunnable);
        runnableThread.start();

    }
}
