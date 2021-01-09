package System;

import Backend.Admin;
import Backend.Customer;
import Backend.DatabaseAccess;
import Backend.Item;
import CustomErrors.InternalServerException;
import CustomErrors.UserDoesNotExist;
import CustomErrors.WrongCredentialsException;

import java.util.Scanner;

public class run {

    private static DatabaseAccess db;

    static {
        try {
            db = new DatabaseAccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public run() throws Exception {
    }

    public static int getLoginChoice(){
        Scanner sc=new Scanner(System.in);
        int choice=0;
        while(true)
        {
            choice=sc.nextInt();
            if(choice==1 | choice ==2 | choice ==3){
                return choice;
            }
            else {
                System.out.println("Wrong choice try again ");
                sc.remove();
            }

        }
    }
    public static void main(String arg[]) throws Exception {
        boolean status=true;
        Scanner sc=new Scanner(System.in);
        int choice=0;
        while(status){
            System.out.println("--------------Login----------");
            System.out.println("enter 1 for admin");
            System.out.println("enter 2 for user");
            System.out.println("enter 3 for delivery boy");

            choice=getLoginChoice();
            if(choice==1){
                //Todo validate the input data
                System.out.print("enter ur user name: ");
                String Uname=sc.next();
                System.out.print("enter ur password: ");
                String Upass=sc.next();
                Admin admin=db.loginAdmin(Uname, Upass);
                int adminChoice;
                while(true){
                    adminChoice=admin.adminMenu();
                    if(adminChoice==1)admin.addDeliveryBoy();
                    else if(adminChoice==2)admin.removeDeliveryBoy();
                    else if(adminChoice==2)admin.updateDeliveryBoyDetails();
                    else if(adminChoice==4)admin.viewDeliveryBoyDetails();
                    else if(adminChoice==5){
                        Item newItem=Item.creatNewItem();
                        admin.addNewProduct(db, newItem);
                    }
                    else if(adminChoice==6){
                        db.showAllItems();
                        //todo validate input
                        int id=sc.nextInt();
                        admin.removeProduct(db, id);
                    }
                    else if(adminChoice==7){
                        db.showAllItems();
                        //todo validate input
                        System.out.println("enter the item id:");int id=sc.nextInt();
                        Item newItem=Item.creatNewItem();
                        admin.updateProductDetails(db, id, newItem);
                    }
                    //todo مش عارف ازاي الصراحه
                    else if(adminChoice==8){
                    }
                    else break;

                }
            }
            else if(choice==2){
                //Todo validate the input data
                System.out.print("enter ur user name: ");
                String Uname=sc.next();
                System.out.print("enter ur password: ");
                String Upass=sc.next();
                Customer user=db.loginUser(Uname, Upass);

            }
        }
    }
}
