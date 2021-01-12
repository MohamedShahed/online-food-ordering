package Backend;

import java.sql.*;
import java.util.ArrayList;

import Backend.Actors.Admin;
import Backend.Actors.Customer;
import Backend.Actors.DeliveryBoy;
import CustomErrors.InternalServerException;
import CustomErrors.UserDoesNotExist;
import CustomErrors.UsernameExistsException;
import CustomErrors.WrongCredentialsException;


public class DatabaseAccess {
    private final String databaseName = "online_food_ordering";
    private final String user = "root";
    private final String password = "Shahed,,  10";
    private Connection connect = null;
    private Statement statement = null;

    public DatabaseAccess() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName + "?user=" +
                    user + "&password=" + password + "&useSSL=false&autoReconnect=true");
            statement = connect.createStatement();
        } catch (Exception e) {

            throw e;
        }
    }

    private boolean userExists(String username) throws InternalServerException {
        try {
            PreparedStatement checkUser = connect.prepareStatement("SELECT COUNT(*) AS TOTAL FROM online_food_ordering.customers WHERE Uname=?;");
            checkUser.setString(1, username);
            ResultSet resultSet = checkUser.executeQuery();

            resultSet.first();
            int y = resultSet.getInt("TOTAL");
            return (y > 0);
        } catch (SQLException s) {
            throw new InternalServerException();
        }
    }
    public boolean registerUser(Customer customer) throws InternalServerException, UsernameExistsException {
        if (userExists(customer.getName())) {
            throw new UsernameExistsException();
        }

        try {
            PreparedStatement insertUser = connect.prepareStatement("INSERT INTO customers (Uid,Uname, Uemail, password, Uaddress, UphoneNumber) VALUES (?, ?, ?, ?, ?)");
            insertUser.setInt(1, customer.getId());
            insertUser.setString(2, customer.getName());
            insertUser.setString(3, customer.getEmail());
            insertUser.setString(4, customer.getPassword());
            insertUser.setString(4, customer.getAddress());
            insertUser.setString(5, customer.getPhoneNumber());
            insertUser.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public Customer loginUser(String username, String pass) throws Exception {
        if (!userExists(username)) {
            throw new UserDoesNotExist();
        }

        try {
            PreparedStatement loginQuery = connect.prepareStatement("SELECT * FROM customers WHERE Uname=?");
            loginQuery.setString(1, username);
            ResultSet result = loginQuery.executeQuery();
            result.first();
            String dbPassword = result.getString("password");
            String email = result.getString("Uemail");
            String Pnumber=result.getString("UphoneNumber");
            String address=result.getString("Uaddress");
            int id=result.getInt("Uid");

            if (dbPassword.equals(pass)) {
                return new Customer(id, username, email,address, Pnumber);
            } else {
                throw new WrongCredentialsException();
            }
        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }

    private boolean adminExists(String adminName) throws InternalServerException {
        try {
            PreparedStatement checkUser = connect.prepareStatement("SELECT COUNT(*) AS TOTAL FROM online_food_ordering.admins WHERE name=?;");
            checkUser.setString(1, adminName);
            ResultSet resultSet = checkUser.executeQuery();

            resultSet.first();
            int y = resultSet.getInt("TOTAL");
            return (y > 0);
        } catch (SQLException s) {
            throw new InternalServerException();
        }
    }
    public Admin loginAdmin(String username, String pass) throws UserDoesNotExist, InternalServerException, WrongCredentialsException {
        if (!adminExists(username)) {
            throw new UserDoesNotExist();
        }

        try {
            PreparedStatement loginQuery = connect.prepareStatement("SELECT * FROM admins WHERE name=?");
            loginQuery.setString(1, username);
            ResultSet result = loginQuery.executeQuery();
            result.first();
            String dbPassword = result.getString("password");
            String email = result.getString("email");
            String PNumber=result.getString("phoneNumber");

            if (dbPassword.equals(pass)) {
                return new Admin(username, email, PNumber);
            } else {
                throw new WrongCredentialsException();
            }
        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }

    public void addNewItem(Item item) throws InternalServerException {
        try {
            PreparedStatement insertItem = connect.prepareStatement("INSERT INTO items (id, title, price, quantity, description) VALUES (?, ?, ?, ?, ?)");
            insertItem.setInt(1, item.getId());
            insertItem.setString(2, item.getTitle());
            insertItem.setFloat(3, item.getPrice());
            insertItem.setInt(4, item.getQuantity());
            insertItem.setString(5, item.getDescription());
            insertItem.executeUpdate();

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }

    public void removeItem(int id) throws InternalServerException {
        try {
            PreparedStatement deleteItem = connect.prepareStatement("DELETE FROM items where id=?");
            deleteItem.setInt(1, id);
            deleteItem.executeUpdate();

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public void showAllItems() throws InternalServerException {
        try{
            PreparedStatement loginQuery = connect.prepareStatement("SELECT * FROM items ");
            ResultSet result = loginQuery.executeQuery();
            int quantity=0;
            while(result.next()){
                quantity=result.getInt("quantity");
                if(quantity>0)
                    System.out.println("id: "+result.getInt("id") +"   title:"+ result.getString("title")+ "   price: " +result.getFloat("price")+ "   quantity"+quantity+"    description  "+result.getString("description"));
            }
        }catch(SQLException e){
            throw  new InternalServerException();
        }
    }
    public ArrayList<Item> getAllItems() throws Exception {
        ArrayList<Item> items=new ArrayList<>();
        try{
            PreparedStatement Query = connect.prepareStatement("SELECT * FROM items ");
            ResultSet result = Query.executeQuery();
            while(result.next()){
                items.add(new Item(result.getInt("id"),result.getString("title"),result.getFloat("price"), result.getInt("quantity")));

            }
            return items;
        }catch(SQLException e){
            throw  new Exception();
        }
    }
    public Item getBuyedItem(int id, int q) throws Exception {
        try{
            PreparedStatement Query = connect.prepareStatement("SELECT * FROM items where id=?");
            Query.setInt(1, id);
            ResultSet result = Query.executeQuery();
            int preQuantity=result.getInt("quantity");
            updateItem(id, new Item(result.getString("title"),result.getFloat("price"),preQuantity-q, result.getString("description")));
            return new Item(result.getString("title"), result.getFloat("price"),q,result.getString("description"));
        }catch(SQLException e){
            throw  new Exception();
        }
    }
    public Item getItem(int id) throws Exception {
        try{
            PreparedStatement Query = connect.prepareStatement("SELECT * FROM items where id=?");
            Query.setInt(1, id);
            ResultSet result = Query.executeQuery();
            return new Item(result.getString("title"), result.getFloat("price"),result.getInt("quantity"),result.getString("description"));

        }catch(SQLException e){
            throw  new Exception();
        }
    }
    public void updateItem(int id, Item item) throws InternalServerException {
        try {
            PreparedStatement updateItem = connect.prepareStatement("update items set title=?, price=?, quantity=? where id=?");
            updateItem.setString(1, item.getTitle());
            updateItem.setFloat(2, item.getPrice());
            updateItem.setFloat(3, item.getQuantity());
            updateItem.setInt(4, id);
            updateItem.executeUpdate();

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }

    public void viewItem(int id) throws InternalServerException{
        try {
            PreparedStatement getItemQuery = connect.prepareStatement("SELECT * FROM items WHERE id=?");
            getItemQuery.setInt(1, id);
            ResultSet result = getItemQuery.executeQuery();
            result.first();
            String dbTitle = result.getString("title");
            String dbPrice = result.getString("price");
            int dbQuantity = result.getInt("quantity");

            if(dbQuantity>0)
            {
            System.out.println("Title"+ "   " + "Price "+ "   ");
            System.out.println(dbTitle+"    "+dbPrice+ "    "+ dbQuantity);
            }

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public void creatOrder(Order order)throws  InternalServerException{
        try {
            PreparedStatement insertItem = connect.prepareStatement("INSERT INTO orders (id, customer_id, duration, customerNotes, PaymentType, total_price,items) VALUES (?, ?, ?, ?, ?, ?)");
            insertItem.setInt(1, order.getId());
            insertItem.setInt(2, order.getCustomer_id());
            insertItem.setString(3, order.getDuration());
            insertItem.setString(4, order.getCustomerNotes());
            insertItem.setString(5, order.getPaymentType());
            insertItem.setString(6, order.getOrderList());
            insertItem.executeUpdate();

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public  int getMaxItemId() throws InternalServerException {
        try {
            PreparedStatement getItemQuery = connect.prepareStatement("select max(id) from items");
            ResultSet result = getItemQuery.executeQuery();
            result.first();
            return result.getInt(1);

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public  int getMaxUserId() throws InternalServerException {
        try {
            PreparedStatement getItemQuery = connect.prepareStatement("select max(Uid) from customers");
            ResultSet result = getItemQuery.executeQuery();
            result.first();
            return result.getInt(1);

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }

    /***Dboy***/
    public  int getMaxDeliveryBoyId() throws InternalServerException {
        try {
            PreparedStatement getItemQuery = connect.prepareStatement("select max(id) from deliveryBoy");
            ResultSet result = getItemQuery.executeQuery();
            result.first();
            return result.getInt(1);

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public void addNewDeliveryBoy(DeliveryBoy Dboy) throws InternalServerException {
        try {
            PreparedStatement insertDeliveryBoy = connect.prepareStatement("INSERT INTO deliveryBoy (id, name, email, password, order_id, branch) VALUES (?, ?, ?, ?, ?, ?)");
            insertDeliveryBoy.setInt(1, Dboy.getId());
            insertDeliveryBoy.setString(2, Dboy.getName());
            insertDeliveryBoy.setString(3, Dboy.getEmail());
            insertDeliveryBoy.setString(4, Dboy.getPassword());
            insertDeliveryBoy.setInt(5, 0);
            insertDeliveryBoy.setString(6, Dboy.getBranch());
            insertDeliveryBoy.executeUpdate();
            System.out.println("the process done successfully");
        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public void showAllDeliveryBoy() throws InternalServerException {
        try{
            PreparedStatement loginQuery = connect.prepareStatement("SELECT * FROM deliveryBoy ");
            ResultSet result = loginQuery.executeQuery();
            while(result.next()){
                System.out.println("id: "+result.getInt("id") +"     "+ "name: "+result.getString("name")+ "       email: " +result.getString("email"));
            }
        }catch(SQLException e){
            throw  new InternalServerException();
        }
    }


    public void removeDeliveryBoy(int id) throws InternalServerException {
        try {
            PreparedStatement deleteItem = connect.prepareStatement("DELETE FROM deliveryBoy where id=?");
            deleteItem.setInt(1, id);
            deleteItem.executeUpdate();

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public void updateDeliveryBoy(int id, DeliveryBoy DBoy) throws InternalServerException {
        try {
            PreparedStatement updateDBoy = connect.prepareStatement("update deliveryBoy set order_id=?, name=?, email=?, password=?, branch=?, weekWorkHours=?, vehicleType=? where id=?");
            updateDBoy.setInt(1, DBoy.getOrderId());
            updateDBoy.setString(2, DBoy.getName());
            updateDBoy.setString(3, DBoy.getEmail());
            updateDBoy.setString(4, DBoy.getPassword());
            updateDBoy.setString(5, DBoy.getBranch());
            updateDBoy.setFloat(6, DBoy.getWeekWorkHours());
            updateDBoy.setString(7, DBoy.getVehicleType());
            updateDBoy.setInt(8,id);
            updateDBoy.executeUpdate();

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public void viewDboy(int id) throws InternalServerException{
        try {
            PreparedStatement getboyQuery = connect.prepareStatement("SELECT * FROM deliveryBoy WHERE id=?");
            getboyQuery.setInt(1, id);
            ResultSet result = getboyQuery.executeQuery();
            result.first();
            String name = result.getString("name");
            String email = result.getString("email");
            String password=result.getString("password");
            String branch=result.getString("branch");
            String Vtype=result.getString("vehicleType");
            float weekWorkHours=result.getFloat("weekWorkHours");

            System.out.println("name:"+name);
            System.out.println("email:"+email);
            System.out.println("password: "+password);
            System.out.println("branch: "+branch);
            System.out.println("vehicle Type: "+Vtype);
            System.out.println("week Work Hours: "+ weekWorkHours);

        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
}
