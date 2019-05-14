package a_DesignPatterns.Iterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class MenuItem {
    private String name;
    private String description;
    private double price;

    public MenuItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
}

interface Menu {
    Iterator<MenuItem> createIterator();
}

class MenuC implements Menu {
    HashMap<String, MenuItem> menuItems = new HashMap<>();

    public MenuC() {
        addItem("C1 ", "Veggie burger", 3.99);
        addItem("C2", "A cup of the soup", 3.69);
        addItem("C3", "A large burrito", 4.29);
    }

    public void addItem(String name, String description, double price) {
        MenuItem menuItem = new MenuItem(name, description, price);
        menuItems.put(menuItem.getName(), menuItem);
    }

    public Map<String, MenuItem> getItems() {
        return menuItems;
    }
    public Iterator<MenuItem> createIterator() {
        return menuItems.values().iterator();
    }
}

class MenuB implements Menu {
    private static final int MAX_ITEMS = 6;
    private int numberOfItems = 0;
    private MenuItem[] menuItems;

    public MenuB() {
        menuItems = new MenuItem[MAX_ITEMS];
        addItem("B1", "Bacon with lettuce", 2.99);
        addItem("B2", "A hot dog", 3.05);
    }

    public void addItem(String name, String description, double price) {
        MenuItem menuItem = new MenuItem(name, description, price);
        if (numberOfItems >= MAX_ITEMS) {
            System.err.println("Sorry, menu is full!  Can't add item to menu");
        } else {
            menuItems[numberOfItems] = menuItem;
            numberOfItems = numberOfItems + 1;
        }
    }

    public MenuItem[] getMenuItems() {
        return menuItems;
    }

    public Iterator<MenuItem> createIterator() {
        return new MenuBIterator(menuItems);
        //return new AlternatingMenuBIterator(menuItems);
    }
}

class MenuBIterator implements Iterator<MenuItem> {
    private MenuItem[] list;
    private int position = 0;

    public MenuBIterator(MenuItem[] list) {
        this.list = list;
    }

    public MenuItem next() {
        MenuItem menuItem = list[position];
        position = position + 1;
        return menuItem;
    }

    public boolean hasNext() { return  !(position >= list.length || list[position] == null); }

    public void remove() {
        if (position <= 0) {
            throw new IllegalStateException("You can't remove an item until you've done at least one next()");
        }
        if (list[position-1] != null) {
            for (int i = position-1; i < (list.length-1); i++) { list[i] = list[i+1]; }
            list[list.length-1] = null;
        }
    }
}

class MenuA implements Menu {
    private ArrayList<MenuItem> menuItems;

    public MenuA() {
        menuItems = new ArrayList<>();
        addItem("A1", "scrambled eggs", 2.99);
        addItem("A2", "fried eggs, sausage", 2.99);
        addItem("A3", "fresh blueberries,", 3.49);
        addItem("A4", "Waffles", 3.59);
    }

    public void addItem(String name, String description, double price) {
        MenuItem menuItem = new MenuItem(name, description, price);
        menuItems.add(menuItem);
    }

    public ArrayList<MenuItem> getMenuItems() { return menuItems; }

    public Iterator<MenuItem> createIterator() { return menuItems.iterator(); }
}

class Waitress {
    private Menu a;
    private Menu b;
    private Menu c;

    public Waitress(Menu a, Menu b, Menu c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void printMenu() {
        Iterator<MenuItem> aIterator = a.createIterator();
        Iterator<MenuItem> bIterator = b.createIterator();
        Iterator<MenuItem> cIterator = c.createIterator();

        System.out.println("MENU\n----\nBREAKFAST");
        printMenu(aIterator);
        System.out.println("\nLUNCH");
        printMenu(bIterator);
        System.out.println("\nDINNER");
        printMenu(cIterator);
    }

    private void printMenu(Iterator<MenuItem> iterator) {
        while (iterator.hasNext()) {
            MenuItem menuItem = iterator.next();
            System.out.print(menuItem.getName() + ", ");
            System.out.print(menuItem.getPrice() + " -- ");
            System.out.println(menuItem.getDescription());
        }
    }
}

public class IteratorDemo {
    public static void main(String[] args) {
        MenuA menuA = new MenuA();
        MenuB menuB = new MenuB();
        MenuC menuC = new MenuC();

        Waitress waitress = new Waitress(menuA, menuB, menuC);

        waitress.printMenu();
    }
}
