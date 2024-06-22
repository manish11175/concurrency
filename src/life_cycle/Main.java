package life_cycle;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main Thread is running");

        try{
            System.out.println("Main thread paused for one second");
            Thread.sleep(1000);
        }
        catch (InterruptedException e){

        }

        Thread thread = new Thread(()->{
            String tname = Thread.currentThread().getName();
            System.out.println(tname+ " should take 10 dots to run");
            for(int i=0;i<10;i++){
                System.out.print(". ");
                try {
                    Thread.sleep(500);
//                    System.out.println("A. State = "+Thread.currentThread().getState());
                }
                catch (InterruptedException e){
//                    System.out.println("B. State = "+ Thread.currentThread().getState());
                    System.out.println("\nWhoops!! "+tname+" Interrupted");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println(tname+" completed");
        });

        Thread installationThread = new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                System.out.println("Installing dependency "+i+1);
                try{
                    Thread.sleep(500);
                }
                catch (InterruptedException interruptedException){
                    System.out.println("Installation is failed!!!!!!!");
                    return;
                }
            }
        },"installThread");




        Thread monitor = new Thread(()->{
            long now = System.currentTimeMillis();
            while(thread.isAlive()){
//                System.out.println("\nwaiting for thread to complete");
                try{
                    Thread.sleep(1000);
//                System.out.println("C. State = "+thread.getState());
                    if(System.currentTimeMillis()-now>2000){
                        thread.interrupt();
                    }
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });


        System.out.println(thread.getName()+" Starting");
        thread.start();
        monitor.start();
        System.out.println("Main Thread would be continue here");


//        System.out.println("D. state "+thread.getState());

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(!thread.isInterrupted()){
            installationThread.start();
        }
        else {
            System.out.println("Previous Thread  was interrupted " + installationThread.getName() + " can't run");
        }
    }
}
