package executors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class ColorThreadFactory implements ThreadFactory{
    private String threadName;
    public ColorThreadFactory(ThreadColor threadColor){
        threadName= threadColor.name();
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
    }
}
public class SingleThreadedExecutor {
    public static void main(String[] args) {
          synchronizedSingleThreadExecutors();
    }

    private static void asynchronizedSingleThreadedExecutors(){
        var  blueExecutor = Executors.newSingleThreadExecutor(
                new ColorThreadFactory(ThreadColor.ANSI_BLUE)
        );
        blueExecutor.execute(Main::countDown);
        blueExecutor.shutdown();

        var  redExecutor = Executors.newSingleThreadExecutor(
                new ColorThreadFactory(ThreadColor.ANSI_RED)
        );
        redExecutor.execute(Main::countDown);
        redExecutor.shutdown();
        var  greenExecutor = Executors.newSingleThreadExecutor(
                new ColorThreadFactory(ThreadColor.ANSI_GREEN)
        );
        greenExecutor.execute(Main::countDown);
        greenExecutor.shutdown();
    }

    private static void synchronizedSingleThreadExecutors(){
        var  blueExecutor = Executors.newSingleThreadExecutor(
                new ColorThreadFactory(ThreadColor.ANSI_BLUE)
        );
        blueExecutor.execute(Main::countDown);
        blueExecutor.shutdown();

        boolean isDone = false;

        try {
            isDone = blueExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(isDone){
            System.out.println("Red Thread will start once Blue thread is finished!!!");
            var  redExecutor = Executors.newSingleThreadExecutor(
                    new ColorThreadFactory(ThreadColor.ANSI_RED)
            );
            redExecutor.execute(Main::countDown);
            redExecutor.shutdown();

            try {
                isDone = redExecutor.awaitTermination(500,TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(isDone){
                System.out.println("Green Thread will start once Red thread is finished!!!");
                var  greenExecutor = Executors.newSingleThreadExecutor(
                        new ColorThreadFactory(ThreadColor.ANSI_GREEN)
                );
                greenExecutor.execute(Main::countDown);
                greenExecutor.shutdown();
                try {
                    isDone = greenExecutor.awaitTermination(500,TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Green Thread is finished");
            }
        }


    }
}
