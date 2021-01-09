package Backend;

import java.util.ArrayList;

public class Order {
    private int id;
    private ArrayList<Item> orderList;
    private String duration;
    private String CustomerNotes;
    private String PaymentType;
    private float cost;


    public ArrayList<Item> getOrderList() {
        return orderList;
    }

}
