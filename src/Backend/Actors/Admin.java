package Backend.Actors;

import Backend.DatabaseAccess;
import Backend.Item;
import CustomErrors.InternalServerException;

import javax.xml.crypto.Data;
import java.util.Scanner;

public class Admin extends Person {
    private String phoneNumber;
    public Admin(String username, String email, String phoneNumber) {

        super(username, email);
        this.phoneNumber=phoneNumber;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    public void addDeliveryBoy(DatabaseAccess db, DeliveryBoy dboy) throws InternalServerException {
        db.addNewDeliveryBoy(dboy);
    }
    public void removeDeliveryBoy(DatabaseAccess db, int id) throws InternalServerException {
        db.removeDeliveryBoy(id);
    }
    public void updateDeliveryBoyDetails(){}
    public void viewDeliveryBoyDetails(){}
    public static void addNewProduct(DatabaseAccess db, Item item) throws InternalServerException {
        db.addNewItem(item);
    }
    public static void removeProduct(DatabaseAccess db, int id) throws InternalServerException {
        db.removeItem(id);
    }
    public void updateProductDetails(DatabaseAccess db,int breveId,Item item)throws InternalServerException{
        db.updateItem(breveId,item);
    }
    public static void viewProductDetails(DatabaseAccess db, int id) throws InternalServerException {
        db.viewItem(id);
    }
    public static void main(String arg[]) throws Exception {
        DatabaseAccess db=new DatabaseAccess();
        Admin.viewProductDetails(db, 2);

    }

    private int validateAdminChoice(){
        Scanner sc=new Scanner(System.in);
        int choice=0;
        while(true)
        {
            choice=sc.nextInt();
           if(choice ==1 | choice ==2 | choice ==3 | choice ==4 | choice ==5 | choice ==6 | choice ==7 | choice ==8 | choice==9){
               return choice;
           }
            System.out.println("Wrong choice try again ");
           sc.remove();
        }
    }
    public int adminMenu(){
        System.out.println("-------welcome Mr: "+ this.name+ "--------");
        System.out.println("enter 1 for adding a new delivery boy ");
        System.out.println("enter 2 for removing delivery boy");
        System.out.println("enter 3 for updating delivery boy details");
        System.out.println("enter 4 for view delivery boy details");
        System.out.println("enter 5 for add a new product");
        System.out.println("enter 6 for remove a specific product");
        System.out.println("enter 7 for update a specific product details");
        System.out.println("enter 8 for view products  ");
        System.out.println("enter 9 for logout ");
        return validateAdminChoice();
    }
}
