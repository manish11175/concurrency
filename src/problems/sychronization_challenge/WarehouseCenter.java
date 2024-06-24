package problems.sychronization_challenge;

import java.util.ArrayList;
import java.util.List;

public class WarehouseCenter {

    public static List<Order> shippingItems;
    public static List<Shoe> shoes = new ArrayList<>();

    static {
        Shoe whiteSneaker = new Shoe(1, "sneaker", "white", 10);
        Shoe boot = new Shoe(2, "boot", "black", 10);
        Shoe whiteSport = new Shoe(3, "sport", "white", 10);
        Shoe blackSport = new Shoe(4, "sport", "black", 10);
        Shoe blackSneaker = new Shoe(5, "sneaker", "black", 10);
        shoes.addAll(List.of(whiteSneaker, blackSneaker, whiteSport, boot, blackSport));
    }

    public WarehouseCenter() {
        this.shippingItems = new ArrayList<>();
    }

    public synchronized void receiveOrder(Order order) {
        while (shippingItems.size() > 6) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        shippingItems.add(order);
        System.out.printf("Order %d is received, quantity = %d and shoe =%s  and color = %s \n", order.getOrderId(), order.getQuantity(), order.getShoe().getType(), order.getShoe().getColor());
        notifyAll();
    }

    public synchronized Order fulfillOrder() {
        while (shippingItems.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Order order = shippingItems.remove(0);

        System.out.printf("%s - Order %d is fulfill, quantity = %d and shoe =%s  and color = %s \n", Thread.currentThread().getName(), order.getOrderId(), order.getQuantity(), order.getShoe().getType(), order.getShoe().getColor());
        notifyAll();
        return order;
    }
}




