package volatile_;

import memory_consistency_error.CachedData;

public class Main {
    private volatile boolean flag = false;
    // volatile are used when resource are shared among the multiple thread and
    // each thread want the updated data each time
    public void toggleFlag(){
        flag=!flag;
    }

    public boolean isReady(){
        return flag;
    }

    public static void main(String[] args) {

        Main example = new Main();
        Thread writeThread = new Thread(()->{
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            example.toggleFlag();
            System.out.println("A. Flag is set to "+example.isReady());
        },"Write Thread");


        Thread readThread = new Thread(()->{
            while(!example.isReady()){
                // Busy until flag becomes true
            }
            System.out.println("B. Flag is "+example.isReady());
        },"Read Thread");

        writeThread.start();
        readThread.start();
    }
}
