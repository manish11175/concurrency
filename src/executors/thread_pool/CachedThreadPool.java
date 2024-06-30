package executors.thread_pool;

import executors.ThreadColor;
import memory_consistency_error.CachedData;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CachedThreadPool {
    public static void main(String[] args) {
       submitCachedThreads();
    }
    public static void submitCachedThreads(){
        var multiExecutor = Executors.newCachedThreadPool();
        try{
            multiExecutor.submit(()->{
                CachedThreadPool.sum(1,10,1,"red");
            });

            multiExecutor.submit(()->{
                CachedThreadPool.sum(10,100,10,"blue");
            });
            multiExecutor.submit(()->{
                CachedThreadPool.sum(2,20,2,"green");
            });
        }
        finally {
            multiExecutor.shutdown();
        }
    }
    public  static void executeCachedThreads(){
        var multiExecutor = Executors.newCachedThreadPool();
        try{
            multiExecutor.execute(()->{
                CachedThreadPool.sum(1,10,1,"red");
            });

            multiExecutor.execute(()->{
                CachedThreadPool.sum(10,100,10,"blue");
            });
            multiExecutor.execute(()->{
                CachedThreadPool.sum(2,20,2,"green");
            });

            multiExecutor.execute(()->{
                CachedThreadPool.sum(1,10,1,"white");
            });

            multiExecutor.execute(()->{
                CachedThreadPool.sum(10,100,10,"yellow");
            });
            multiExecutor.execute(()->{
                CachedThreadPool.sum(2,20,2,"black");
            });

            try{
                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e){
                throw new RuntimeException();
            }

            System.out.println("Next Task will get executed");
            for(String color:new String[]{"red","blue","yellow","white","black","purple","green","cyan"}){
                multiExecutor.execute(()->{
                    CachedThreadPool.sum(1,10,1,color);
                });
            }
        }
        finally {
            multiExecutor.shutdown();
        }
    }

    public static void sum(int start,int end, int delta,String colorString){
        var threadColor = ThreadColor.ANSI_RESET;
        try{
            threadColor = ThreadColor.valueOf("ANSI_"+colorString.toUpperCase());
        }
        catch (IllegalArgumentException ignore){
            // user may pass bad color
        }

        String color = threadColor.getColor();
        int sum = 0;
        for(int i=start;i<=end;i+=delta){
            sum+=i;
        }
        System.out.println(color+Thread.currentThread().getName()+", "+colorString+" "+sum);
    }
}
