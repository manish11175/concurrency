package executors.future;


import executors.ThreadColor;

import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        invokeAllMethod();
    }
    public static void invokeAllMethod(){
        var multiExecutor = Executors.newCachedThreadPool();

        List<Callable<Integer>> taskList= List.of(
                ()->Main.sum(1,10,1,"blue"),
                ()->Main.sum(10,100,10,"red"),
                ()->Main.sum(2,20,2,"green"));
        try {
//            var resultList = multiExecutor.invokeAll(taskList);
//            for(var result:resultList){
//                System.out.println(result.get(500,TimeUnit.MILLISECONDS));
//            }

            var result = multiExecutor.invokeAny(taskList);
            System.out.println(result);
        } catch (InterruptedException |ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    public static void futureInterface() {

        var multiExecutor = Executors.newCachedThreadPool();
        try{
            var blueSum=multiExecutor.submit(()->
                Main.sum(1,10,1,"blue"));

            var redSum=multiExecutor.submit(()->
                Main.sum(10,100,10,"red"));
            var greenSum=multiExecutor.submit(()->Main.sum(2,20,2,"green"));
            try{
                System.out.println(blueSum.get(500, TimeUnit.MILLISECONDS));
                System.out.println(redSum.get(500, TimeUnit.MILLISECONDS));
                System.out.println(greenSum.get(500, TimeUnit.MILLISECONDS));
            }
            catch (InterruptedException | TimeoutException | ExecutionException e){
                throw new RuntimeException();
            }
        }
        finally {
            multiExecutor.shutdown();
        }
    }

    private static int sum(int start,int end,int delta,String colorString){
        var threadColor = ThreadColor.ANSI_RESET;

        try{
            threadColor = ThreadColor.valueOf("ANSI_"+colorString.toUpperCase());
        }
        catch (IllegalArgumentException ignore){
            //user can pass bad color
        }
        int sum = 0;
        for(int i=start;i<=end;i+=delta){
            sum+=i;
        }
        String color = threadColor.getColor();
        System.out.println(color+Thread.currentThread().getName()+", "+colorString+" "+sum);
        return sum;
    }
}
