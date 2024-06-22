package problems.task1;

public class Main {

    public static void main(String[] args) {
        Thread evenThread = new PrintEvenThread(5,"PrintEvenThread");

        Thread oddThread = new Thread(()->{
            try{
                for (int i = 0; i < 5; i++) {
                    System.out.println((2*i+1)+" ");
                    Thread.sleep(500);
                }
            }
            catch(InterruptedException e){
                System.out.println(Thread.currentThread().getName()+" is interrupted!!!!!!!");
                Thread.currentThread().interrupt();
            }
        },"PrintOddThread");


        Thread monitorThread = new Thread(()->{
            long now = System.currentTimeMillis();
            while(evenThread.isAlive() && oddThread.isAlive()){
                if(System.currentTimeMillis()-now>2000){
                    oddThread.interrupt();
                    evenThread.interrupt();
                }
            }
        },"Monitor Thread");

        evenThread.start();
        oddThread.start();
        monitorThread.start();
    }
}
