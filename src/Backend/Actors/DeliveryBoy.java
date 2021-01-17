package Backend.Actors;

import Backend.DatabaseAccess;
import CustomErrors.InternalServerException;

import java.util.Scanner;

public class DeliveryBoy extends Person {
    private DatabaseAccess db=new DatabaseAccess();
    private int id=db.getMaxId("deliveryBoy");

    //todo adding them using admin
    private String branch;
    private String evaluation;
    private String vehicleType;
    private float weekWorkHours;
    private static Scanner sc=new Scanner(System.in);
    private boolean status=false;

    private int OrderId;

    public DeliveryBoy(int id, String name, String email, String password, String branch, float weekWHours, String Vtype) throws Exception {
        super(name, email);
        this.id=id;
        this.name=name;
        this.password=password;
        this.branch=branch;
        this.weekWorkHours=weekWHours;
        this.vehicleType=Vtype;

    }
    public DeliveryBoy(String name, String email, String password, String branch, float weekWHours, String Vtype) throws Exception {
        super(name, email);
        this.id++;
        this.name=name;
        this.password=password;
        this.branch=branch;
        this.weekWorkHours=weekWHours;
        this.vehicleType=Vtype;

    }
    /**this for updating method*/
    public static DeliveryBoy creatDboy(int id) throws Exception {
        System.out.println("enter new name: ");String name=sc.next();
        System.out.println("enter new email: ");String email=sc.next();
        System.out.println("enter new password: ");String password=sc.next();
        System.out.println("enter new branch: ");String branch=sc.next();
        System.out.println("enter new weekWorkHours");float weekWHours=sc.nextFloat();
        System.out.println("enter new vehicleType: ");String Vtype=sc.next();
        return new DeliveryBoy(id, name, email, password, branch, weekWHours, Vtype);
    }
    public static DeliveryBoy creatDboy() throws Exception {
        System.out.println("enter his name: ");String name=sc.next();
        System.out.println("enter his email: ");String email=sc.next();
        System.out.println("enter his password: ");String password=sc.next();
        System.out.println("enter his branch: ");String branch=sc.next();
        System.out.println("enter his week Work Hours");float weekWHours=sc.nextFloat();
        System.out.println("enter his vehicleType: ");String Vtype=sc.next();
        return new DeliveryBoy(name, email, password, branch, weekWHours, Vtype);
    }
    @Override
    public String getName() {
        return this.name;
    }
    public int getId(){return id;}
    public String getPassword(){return password;}
    public int getOrderId(){return this.OrderId;}
    public String getVehicleType(){return this.vehicleType;}
    public float getWeekWorkHours(){return this.weekWorkHours;}
    @Override
    public String getEmail() {
        return this.email;
    }

    public String getBranch(){return this.branch;}

}
