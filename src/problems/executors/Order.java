package problems.executors;

public class Order {
    private int orderId;
    private int quantity;
    private Shoe shoe;

    public Order(int orderId, int quantity, Shoe shoe) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.shoe = shoe;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }
}
