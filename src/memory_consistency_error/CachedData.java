package memory_consistency_error;

import multiple_threads.ThreadColor;

public class CachedData {

    private boolean flag = false;

    public void toggleFlag(){
        flag=!flag;
    }

    public boolean isReady(){
        return flag;
    }

    public static void main(String[] args) {

        CachedData cachedData = new CachedData();
        Thread writeThread = new Thread(()->{
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            cachedData.toggleFlag();
            System.out.println("A. Flag is set to "+cachedData.isReady());
        },"Write Thread");


        Thread readThread = new Thread(()->{
            while(!cachedData.isReady()){
                // Busy until flag becomes true
            }
            System.out.println("B. Flag is "+cachedData.isReady());
        },"Read Thread");

        writeThread.start();
        readThread.start();
    }
}
