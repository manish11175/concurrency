package problems.executors;


import java.util.Random;
import java.util.concurrent.Executors;

class TakeOrder {

    private static WarehouseCenter warehouseCenter;

    public TakeOrder(WarehouseCenter warehouseCenter) {
        this.warehouseCenter = warehouseCenter;
    }


    public void takeOrder() {
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            warehouseCenter.receiveOrder(new Order(random.nextInt(100000, 999999), 1, warehouseCenter.shoes.get(random.nextInt(0, 5))));
            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class CompleteOrder {
    private static WarehouseCenter warehouseCenter;

    public CompleteOrder(WarehouseCenter warehouseCenter) {
        this.warehouseCenter = warehouseCenter;
    }


    public void completeOrder() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Order order = warehouseCenter.fulfillOrder();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        WarehouseCenter warehouseCenter = new WarehouseCenter();
        int count =4;
        var executors= Executors.newFixedThreadPool(count);
        var takeOrder = new TakeOrder(warehouseCenter);
        executors.execute(takeOrder::takeOrder);
        var completeOrder = new CompleteOrder(warehouseCenter);
        for (int i = 0; i <count ; i++) {
            executors.execute(completeOrder::completeOrder);
        }

        executors.shutdown();

    }
}
