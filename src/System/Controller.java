package System;

import Backend.Actors.Admin;
import Backend.Actors.Customer;
import Backend.Actors.DeliveryBoy;
import Backend.DatabaseAccess;
import Backend.Item;
import Backend.Order;

import java.util.Scanner;

public class Controller {

    private  DatabaseAccess db;
    public Controller() throws Exception {
        {
            db=new DatabaseAccess();
            Scanner sc=new Scanner(System.in);
            int choice=0;
            while(true){
                System.out.println("--------------Welcome----------");
                System.out.println("enter 1 for admin login");
                System.out.println("enter 2 for user  login");
                System.out.println("enter 3 for delivery boy login");
                System.out.println("enter 4 for user registration");
                System.out.println("enter 5 to Exit");

                choice=getLoginChoice();
                if(choice==1){    /**Admin*/
                    //Todo validate the input data
                    System.out.print("enter ur user name: ");
                    String Uname=sc.next();
                    System.out.print("enter ur password: ");
                    String Upass=sc.next();
                    Admin admin=db.loginAdmin(Uname, Upass);
                    int adminChoice;
                    while(true){
                        adminChoice=admin.adminMenu();
                        if(adminChoice==1){ /** 1 for adding a new delivery boy*/
                            DeliveryBoy dBoy=DeliveryBoy.creatDboy();
                            admin.addDeliveryBoy(db,dBoy);

                        }
                        else if(adminChoice==2) /**2 for removing delivery boy*/
                        {
                            db.showAllDeliveryBoy();
                            System.out.println("enter delivery boy id: ");int id=sc.nextInt();
                            admin.removeDeliveryBoy(db,id);

                        }
                        else if(adminChoice==3)/** 3 for updating delivery boy details*/
                        {
                            db.showAllDeliveryBoy();
                            System.out.println("enter delivery boy id: ");int id=sc.nextInt();
                            DeliveryBoy newDboy=DeliveryBoy.creatDboy(id);
                            admin.updateDeliveryBoyDetails(db, id, newDboy);
                        }
                        else if(adminChoice==4)/**4 for view delivery boy details*/
                        {
                            db.showAllDeliveryBoy();
                            System.out.println("enter delivery boy id: ");int id=sc.nextInt();
                            admin.viewDeliveryBoyDetails(db,id);
                        }
                        else if(adminChoice==5){ /** 5 for add a new product*/
                            Item newItem=Item.creatNewItem();
                            admin.addNewProduct(db, newItem);
                        }
                        else if(adminChoice==6){ /**6 for remove a specific product*/
                            db.showAllItems();
                            //todo validate input
                            int id=sc.nextInt();
                            admin.removeProduct(db, id);
                        }
                        else if(adminChoice==7){ /**7 for update a specific product details*/
                            db.showAllItems();
                            //todo validate input
                            System.out.println("enter the item id:");int id=sc.nextInt();
                            Item newItem=Item.creatNewItem();
                            admin.updateProductDetails(db, id, newItem);
                        }
                        else if(adminChoice==8){ /**8 for view all products */
                            db.showAllItems();
                        }
                        else break; /**logout*/

                    }
                }
                else if(choice==2){   /**Customer*/
                    //Todo validate the input data
                    System.out.print("enter ur user name: ");
                    String Uname=sc.next();
                    System.out.print("enter ur password: ");
                    String Upass=sc.next();
                    Customer customer=db.loginUser(Uname, Upass);
                    int customerChoice;
                    while(true){
                        customerChoice=customer.Menu();
                        if(customerChoice==1){ /** 1 for product searching using name*/

                        }
                        else if(customerChoice==2) /**2 for make order*/
                        {
                            db.showAllItems();
                            String input;
                            int id, quantity;
                            while(true){
                                System.out.println("to add to ur cart enter y else enter E");input=sc.next();
                                if(input.equals("y")){
                                    System.out.println("enter product id and the quantity u need ");
                                    id=sc.nextInt();quantity=sc.nextInt();
                                    customer.addToCart(id, quantity);
                                }
                                else {
                                    customer.creatOrder(new Order());
                                    break;
                                }
                            }
                        }
                        else if(customerChoice==3)/** 3 for view product details */
                        {
                            db.showAllItems();
                            System.out.println("enter the product id: ");int id=sc.nextInt();
                            System.out.println("the product Description is: "+ db.getItem(id).getDescription());
                            System.out.println("the product evaluation is : "+ db.getItem(id).getEvaluation());

                        }
                        else if(customerChoice==4)/**4 for view product evaluation*/
                        {
                            db.showAllItems();
                            System.out.println("enter the product id: ");int id=sc.nextInt();
                            System.out.println("the product evaluation is : "+ db.getItem(id).getEvaluation());
                        }
                        else if(customerChoice==5){ /** 5 for view order track*/

                        }
                        else if(customerChoice==6){ /**6 for sending issues*/

                        }
                        else if(customerChoice==7){ /**7 for logout*/
                            break;
                        }

                    }

                }
                else if(choice==3){
                    //todo delivery boy
                }
                else if(choice==4){
                    //todo user register
                    System.out.print("enter ur name: ");String name=sc.nextLine();System.out.println();
                    System.out.print("enter ur email: ");String email=sc.next();System.out.println();
                    System.out.print("enter ur password: ");String password=sc.next();System.out.println();
                    System.out.print("enter ur phone number: ");String PhoneNumber=sc.next();System.out.println();sc.nextLine();
                    System.out.print("enter ur Address: ");String address=sc.nextLine();System.out.println();
                    if(db.registerUser(new Customer(name, password, email, address, PhoneNumber)))
                        System.out.println("the registration process done successfully");
                }
                else break;
            }
        }
    }

    public static int getLoginChoice(){
        Scanner sc=new Scanner(System.in);
        int choice=0;
        while(true)
        {
            choice=sc.nextInt();
            if(choice==1 | choice ==2 | choice ==3| choice ==4| choice ==5){
                return choice;
            }
            else {
                System.out.println("Wrong choice try again ");
                sc.remove();
            }

        }
    }

}
