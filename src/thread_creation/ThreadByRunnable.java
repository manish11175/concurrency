package thread_creation;

import java.util.concurrent.TimeUnit;

public class ThreadByRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("Chauhan");
        try{
            TimeUnit.MICROSECONDS.sleep(750);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
