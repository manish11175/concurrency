package problems.sychronization_challenge;


import java.util.Random;

class TakeOrder implements Runnable {

    private WarehouseCenter warehouseCenter;

    public TakeOrder(WarehouseCenter warehouseCenter) {
        this.warehouseCenter = warehouseCenter;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            warehouseCenter.receiveOrder(new Order(random.nextInt(100000, 999999), 1, warehouseCenter.shoes.get(random.nextInt(0, 5))));
            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class CompleteOrder implements Runnable {
    private WarehouseCenter warehouseCenter;

    public CompleteOrder(WarehouseCenter warehouseCenter) {
        this.warehouseCenter = warehouseCenter;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Order order = warehouseCenter.fulfillOrder();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        WarehouseCenter warehouseCenter = new WarehouseCenter();

        Thread takeOrder = new Thread(new TakeOrder(warehouseCenter));
        Thread processOrderThread1 = new Thread(new CompleteOrder(warehouseCenter));
        Thread processOrderThread2 = new Thread(new CompleteOrder(warehouseCenter));
        takeOrder.start();
        processOrderThread1.start();
        processOrderThread2.start();
    }
}
