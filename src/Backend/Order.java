package Backend;

import java.util.ArrayList;
import java.util.Scanner;

public class Order {
    private DatabaseAccess db=new DatabaseAccess();
    private int id=db.getMaxId("orders");
    private int customer_id;
    private ArrayList<Integer> orderList;
    private String duration;
    private String CustomerNotes;
    private String PaymentType;
    private float totalPrice;
    private Scanner sc=new Scanner(System.in);


    public Order() throws Exception {
        System.out.println("enter the payment type :");this.setPaymentType(sc.next());
        System.out.println("do u have any notes ? if yes enter y else enter n");
        String input=sc.next();
        if(input.contains("y")){
            System.out.println("enter ur notes: ");sc.nextLine();
            setCustomerNotes(sc.nextLine());
        }
        id++;

    }
    public int getId() {
        return this.id;
    }
    public float getTotalPrice(){return this.totalPrice;}
    public void setCustomer_id(int id){this.customer_id=id;}
    public void setItems(ArrayList<Item> items){
        orderList=new ArrayList<>(0);
        for(int i=0; i<items.size(); i++){
            orderList.add(items.get(i).getId());
        }
    }
    public void setDuration(String duration){this.duration=duration;}
    public void setPaymentType(String PT){this.PaymentType=PT;}
    public void setCustomerNotes(String notes){this.CustomerNotes=notes;}
    public int getCustomer_id(){return this.customer_id;}
    public String getDuration(){return this.duration;}
    public String getCustomerNotes(){return this.CustomerNotes;}
    public String getPaymentType(){return this.PaymentType;}
    public String getOrderList(){
        String list="";
        for(int i=0; i<this.orderList.size(); i++)
        {
            list+=orderList.get(i).toString();
        }
        return list;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice=totalPrice;
    }
}
