package Backend;

import CustomErrors.InternalServerException;

import java.util.Scanner;

public class Item {
    private DatabaseAccess db=new DatabaseAccess();
    private int id=db.getMaxItemId();
    private float price;
    private int quantity;
    private String title;


    Item(String title, float price, int quantity) throws Exception {
        this.title = title;
        this.price = price;
        this.quantity=quantity;
        this.title=title;
        id++;
    }
    Item(int id, String title, float price, int quantity) throws Exception {
        this.id=id;
        this.title=title;
        this.price=price;
        this.quantity=quantity;
    }
    public static Item creatNewItem() throws Exception {
        String title;
        float price;
        int Q;
        Scanner sc=new Scanner(System.in);
        System.out.print("enter the item title: ");title=sc.nextLine();System.out.println();
        System.out.print("enter the item price: ");price=sc.nextFloat();System.out.println();
        System.out.print("enter the item quantity: ");Q=sc.nextInt();System.out.println();
        return new Item(title, price, Q);

    }

    public String getTitle() {
        return title;
    }
    public float getPrice(){return this.price;}
    public int getQuantity() {
        return quantity;
    }

    public int getId(){return id;}


    public void setTitle(String title){this.title=title;}
    public void setPrice(float price){this.price=price;}
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
