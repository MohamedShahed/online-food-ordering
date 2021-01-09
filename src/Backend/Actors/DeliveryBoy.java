package Backend.Actors;

import Backend.DatabaseAccess;

import java.util.Scanner;

public class DeliveryBoy extends Person {
    private DatabaseAccess db=new DatabaseAccess();
    private int id=db.getMaxDeliveryBoyId();

    public DeliveryBoy(String username, String email, String password) throws Exception {
        super(username, email);
        this.password=password;
        id++;
    }

    @Override
    public String getName() {
        return this.name;
    }
    public int getId(){return id;}
    public String getPassword(){return password;}

    @Override
    public String getEmail() {
        return this.email;
    }

    public static DeliveryBoy creatDboy() throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the DeliveryBoy name: ");String name=sc.next();
        System.out.println("enter the DeliveryBoy email:");String email=sc.next();
        System.out.println("enter the DeliveryBoy password:");String password=sc.next();
        return new DeliveryBoy(name, email, password);
    }
}
