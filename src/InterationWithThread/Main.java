package InterationWithThread;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main Thread is running");
        try{
            System.out.println("Main Thread paused for 1 second");
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        Thread thread = new Thread(()->{
            String tname = Thread.currentThread().getName();
            System.out.println(tname+" should take 10 dots to run");
            for (int i = 0; i < 10; i++) {
                System.out.print(". ");
                try {
                    Thread.sleep(500);
                    System.out.println("A. State = "+ Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    System.out.println("A. State = "+ Thread.currentThread().getState());
                    System.out.println("\nWhoops!!"+tname+" interrupted");
                    return;
                }
            }
             System.out.println("\n"+tname+" complete");
        });
        System.out.println(thread.getName()+" starting");
        thread.start();
//        System.out.println("Main Thread would continue here");
//        try{
//            Thread.sleep(2000);
//        }
//        catch (InterruptedException e){
//            e.printStackTrace();
//        }
//        // manually interrupting the thread
//        thread.interrupt();

        long now = System.currentTimeMillis();
        while(thread.isAlive()){
            System.out.println("\nwaiting for thread to complete");
            try {
                Thread.sleep(1000);
                System.out.println("B. State = "+thread.getState());
                if(System.currentTimeMillis()-now>2000){
                    thread.interrupt();
                }
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println( "C. State = "+thread.getState());
    }
}
