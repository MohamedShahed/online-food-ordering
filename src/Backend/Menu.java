package Backend;

import java.util.ArrayList;

public class Menu {
    private DatabaseAccess db=new DatabaseAccess();
    private ArrayList<Item> items;

    public Menu() throws Exception {
        loadAllItems();
    }

    private void loadAllItems() throws Exception {
        items=db.getAllItems();
    }
    public void showMenu(){
        System.out.println("-------our menu ---------");
        for(int i=0; i<items.size(); i++){
            System.out.println("id: "+items.get(i).getId()+ "       title:"+items.get(i).getTitle()+"     price:"+items.get(i).getPrice()+"   quantity:"+items.get(i).getQuantity());
        }
    }
    public static void main(String arg[]) throws Exception {
        Menu m=new Menu();
        m.showMenu();

    }
}
