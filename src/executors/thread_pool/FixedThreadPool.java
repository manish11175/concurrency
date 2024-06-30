package executors.thread_pool;

import executors.Main;
import executors.SingleThreadedExecutor;
import executors.ThreadColor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ColorThreadFactory implements ThreadFactory {
    private String threadName ;
    private int colorValue =1;

    public ColorThreadFactory(){

    }
    public ColorThreadFactory(ThreadColor threadColor){
        threadName= threadColor.name();
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        String name = threadName;
        if(name==null){
            name=ThreadColor.values()[colorValue].name();
        }
        if(++colorValue>(ThreadColor.values().length-1)){
            colorValue=1;
        }
        thread.setName(name);
        return thread;
    }
}
public class FixedThreadPool {
    public static void main(String[] args) {
        int count = 6;
        var multiExecutor = Executors.newFixedThreadPool(count,new ColorThreadFactory());
        for (int i = 0; i <count ; i++) {
            multiExecutor.execute(Main::countDown);
        }
        multiExecutor.shutdown();
    }
}
