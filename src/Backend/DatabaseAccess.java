package Backend;

import java.sql.*;
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
    public Customer registerUser(String username, String pass, String email) throws InternalServerException, UsernameExistsException {
        if (userExists(username)) {
            throw new UsernameExistsException();
        }

        try {
            PreparedStatement insertUser = connect.prepareStatement("INSERT INTO customers (Uname, Uemail, password) VALUES (?, ?, ?)");
            insertUser.setString(1, username);
            insertUser.setString(2, email);
            insertUser.setString(3, pass);
            insertUser.executeUpdate();

            return new Customer(username, email);
        } catch (SQLException e) {
            throw new InternalServerException();
        }
    }
    public Customer loginUser(String username, String pass) throws UserDoesNotExist, InternalServerException, WrongCredentialsException {
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

            if (dbPassword.equals(pass)) {
                return new Customer(username, email);
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
    public static void main(String arg[]) throws Exception {
        DatabaseAccess db=new DatabaseAccess();
        String name="shahed";
        String email="m.elshahed@gmail.com";
        String password="20160214";

        Admin cus=db.loginAdmin(name, password);
        System.out.println(cus.getName());

    }

    public void addNewItem(Item item) throws InternalServerException {
        try {
            PreparedStatement insertItem = connect.prepareStatement("INSERT INTO items (id, title, price, quantity) VALUES (?, ?, ?, ?)");
            insertItem.setInt(1, item.getId());
            insertItem.setString(2, item.getTitle());
            insertItem.setFloat(3, item.getPrice());
            insertItem.setInt(4, item.getQuantity());
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
            System.out.println("id  " + "title                   " + "  price " + "  quantity");
            while(result.next()){
                System.out.println(result.getInt("id") +"   "+ result.getString("title")+ "         " +result.getFloat("price")+ "   "+result.getInt("quantity"));
            }
        }catch(SQLException e){
            throw  new InternalServerException();
        }
    }
    public void updateItem(int breveId, Item item) throws InternalServerException {
        try {
            PreparedStatement updateItem = connect.prepareStatement("delete FROM items where id=?");
            updateItem.setInt(1, breveId);
            updateItem.executeUpdate();
            addNewItem(item);

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
            String dbQuantity = result.getString("quantity");

            System.out.println("Title"+ "   " + "Price "+ "   ");
            System.out.println(dbTitle+"    "+dbPrice+ "    "+ dbQuantity);

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





}
