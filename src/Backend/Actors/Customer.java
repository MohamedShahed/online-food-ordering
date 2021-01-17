package Backend.Actors;

import Backend.Actors.Person;
import Backend.DatabaseAccess;
import Backend.Item;
import Backend.Order;
import CustomErrors.InternalServerException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customer extends Person {
    private String phoneNumber;
    private String address;
    private String password;

    private ArrayList<Item> cart=new ArrayList<>(0);
    private DatabaseAccess db=new DatabaseAccess();;


    private int id=db.getMaxId("customers");

    public Customer(int id, String name, String email,String address, String phoneNumber) throws Exception {
        super(name, email);
        this.address=address;
        this.phoneNumber=phoneNumber;
    }
    public Customer(String name, String email,String address, String phoneNumber, String password) throws Exception {
        super(name, email);
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.password=password;
        id++;
    }

    public void addToCart(int itemId, int quantity) throws Exception {
        cart.add(db.getBuyedItem(itemId, quantity));
        System.out.println("the item has been added into ur cart ");
    }
    public void creatOrder(Order order) throws InternalServerException {
        order.setCustomer_id(this.getId());
        order.setTotalPrice(getTotalPrice());
        order.setDuration("30 M");
        order.setItems(cart);
        order.setTotalPrice(getTotalPrice());
        db.creatOrder(order);
    }


    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
    public String getPassword(){return this.password; }
    public String getAddress(){return this.address;}
    public int getId(){return this.id;}

    public String getEmail() {
        return email;
    }
    public String getPhoneNumber(){return this.phoneNumber;}



    private int validateCustomerChoice(){
        Scanner sc=new Scanner(System.in);
        int choice=0;
        while(true)
        {
            choice=sc.nextInt();
            if(choice ==1 | choice ==2 | choice ==3 | choice ==4 | choice ==5 | choice ==6 | choice ==7 ){
                return choice;
            }
            System.out.println("Wrong choice try again ");
            sc.remove();
        }
    }
    public int Menu(){
        System.out.println("-------welcome Mr: "+ this.name+ "--------");
        System.out.println("enter 1 for product searching using name");
        System.out.println("enter 2 for make order");
        System.out.println("enter 3 for view product details");
        System.out.println("enter 4 for view product evaluation");
        System.out.println("enter 5 for view order track");
        System.out.println("enter 6 for sending issues");
        System.out.println("enter 7 for logout");
        return validateCustomerChoice();
    }








    public void updateCart(ArrayList<Item> updatedOrder) {
        for (int i = 0; i < cart.size(); i++) {
            cart.get(i).setQuantity(updatedOrder.get(i).getQuantity());
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
