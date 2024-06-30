package problems.executors;

import executors.ThreadColor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Solutions {
    private final static Random random = new Random();
    public static void main(String[] args) {
        WarehouseCenter warehouseCenter = new WarehouseCenter();

        var orderService = Executors.newCachedThreadPool();
//        Callable<Order> orderCallable = ()->{
//            Order order = generateOrder(warehouseCenter);
//            try{
//                Thread.sleep(random.nextInt(1000,5000));
//                warehouseCenter.receiveOrder(order);
//            }
//            catch (InterruptedException e){
//                throw new RuntimeException();
//            }
//            return order;
//        };
//
//        List<Callable<Order>> tasks = Collections.nCopies(15,orderCallable);
//        try {
//            orderService.invokeAll(tasks);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        finally {
//            orderService.shutdown();
//        }

        try{
//            Thread.sleep(random.nextInt(500,5000));
            for (int i = 0; i < 15; i++) {
                Thread.sleep(random.nextInt(500,5000));
                orderService.execute(()-> warehouseCenter.receiveOrder(generateOrder(warehouseCenter)));
            }
        }
        catch (InterruptedException e){
            throw new RuntimeException();
        }
        try {
            orderService.awaitTermination(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        warehouseCenter.shutdown();
    }

    private static Order generateOrder(WarehouseCenter warehouseCenter){

        return new Order(random.nextInt(100000, 999999), 1, warehouseCenter.shoes.get(random.nextInt(0, 5)));
    }
}
