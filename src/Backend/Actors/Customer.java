package Backend.Actors;

import Backend.Actors.Person;
import Backend.Item;
import Backend.Order;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Customer extends Person {

    private ArrayList<Item> cart = new Order().getOrderList();


  //  public ArrayList<Item>creatOrder(){}

    public Customer(String name, String email) {
        super(name, email);
    }

    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void updateCart(ArrayList<Integer> updatedOrder) {
        for (int i = 0; i < cart.size(); i++) {
            cart.get(i).setQuantity(updatedOrder.get(i));
        }
    }

    public ArrayList<Item> getCart() {
        return cart.stream()
                .filter(p -> p.getQuantity() > 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public float getTotalPrice() {
        float totalPrice = 0;

        for (Item item : cart) {
            totalPrice = totalPrice + (item.getPrice() * item.getQuantity());
        }

        return totalPrice;
    }

    public double getTaxedPrice() {
        float totalPrice = getTotalPrice();
        return ((double) totalPrice + (0.18 * totalPrice));
    }

    public String getTax() {
        return String.format("%.2f", 0.18 * getTotalPrice());
    }
}
