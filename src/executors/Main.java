package executors;

public class Main {
    public static void main(String[] args) {

        Thread blue = new Thread(Main::countDown,ThreadColor.ANSI_BLUE.name());
        Thread yellow = new Thread(Main::countDown,ThreadColor.ANSI_YELLOW.name());
        Thread red = new Thread(Main::countDown,ThreadColor.ANSI_RED.name());
        Thread green = new Thread(Main::countDown,ThreadColor.ANSI_GREEN.name());


        blue.start();
        try {
            blue.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        yellow.start();
        try {
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        red.start();
        try {
            red.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        green.start();
        try {
            green.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }






    }

    public static void countDown(){
        String threadName = Thread.currentThread().getName();
        var threadColor = ThreadColor.ANSI_RESET;
        try{
            threadColor=ThreadColor.valueOf(threadName.toUpperCase());
        }
        catch (IllegalArgumentException e){
            //User may pass a bad color name, we just ignore this error.
        }

        String color = threadColor.getColor();
        for (int i = 20; i >0 ; i--) {
            System.out.println(color+" "+threadName.replace("ANSI_","")+" "+i);
        }
    }
}
