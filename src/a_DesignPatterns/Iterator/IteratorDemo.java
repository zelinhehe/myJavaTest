package a_DesignPatterns.Iterator;

import java.util.*;

// 菜单项
class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
}

// 菜单的迭代器接口
interface Menu {
    Iterator<MenuItem> createIterator();
}
// 菜单C 采用HashMap存储
class MenuC implements Menu {
    private HashMap<String, MenuItem> menuItems = new HashMap<>();

    public MenuC() {
        addItem("C1", 3.99);
        addItem("C2", 3.69);
        addItem("C3", 4.29);
    }

    public void addItem(String name, double price) {
        MenuItem menuItem = new MenuItem(name, price);
        menuItems.put(menuItem.getName(), menuItem);
    }
    public Map<String, MenuItem>  getMenuItems() {
        return menuItems;
    }
    public Iterator<MenuItem> createIterator() { return menuItems.values().iterator(); }
}
// 菜单B，采用数组存储
class MenuB implements Menu {
    private static final int MAX_ITEMS = 6;
    private int numberOfItems = 0;
    private MenuItem[] menuItems;

    public MenuB() {
        menuItems = new MenuItem[MAX_ITEMS];
        addItem("B1", 2.99);
        addItem("B2", 3.05);
    }

    public void addItem(String name, double price) {
        MenuItem menuItem = new MenuItem(name, price);
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
    public Iterator<MenuItem> createIterator() { return new MenuBIterator(menuItems); }
}
// 菜单A，采用ArrayList存储
class MenuA implements Menu {
    private ArrayList<MenuItem> menuItems;

    public MenuA() {
        menuItems = new ArrayList<>();
        addItem("A1", 2.99);
        addItem("A2", 2.99);
        addItem("A3", 3.49);
    }

    public void addItem(String name, double price) {
        MenuItem menuItem = new MenuItem(name, price);
        menuItems.add(menuItem);
    }
    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }
    public Iterator<MenuItem> createIterator() { return menuItems.iterator(); }
}
// 服务员，管理菜单
class Waitress {
    private ArrayList<Menu> menus = new ArrayList<>();

    public Waitress(Menu ...menus) { this.menus.addAll(Arrays.asList(menus)); }
    // 使用Iterator的菜单输出
    public void printMenu() {
        for (Menu menu : this.menus) {
            Iterator<MenuItem> iterator = menu.createIterator();
            printMenu(iterator);
        }
    }
    private void printMenu(Iterator<MenuItem> iterator) {
        while (iterator.hasNext()) {
            MenuItem menuItem = iterator.next();
            System.out.print("name: " + menuItem.getName() + " price: " + menuItem.getPrice() + "\n");
        }
    }
    // 不使用Iterator的菜单输出
    public void printMenuWithoutIterator() {
        MenuA menuA = (MenuA) this.menus.get(0);
        MenuB menuB = (MenuB)this.menus.get(1);
        MenuC menuC = (MenuC) this.menus.get(2);
        for (MenuItem menuItem : menuA.getMenuItems()) {
            System.out.print("--name: " + menuItem.getName() + " price: " + menuItem.getPrice() + "\n");
        }
        for (MenuItem menuItem : menuB.getMenuItems()) {
            if (menuItem != null) {
                System.out.print("--name: " + menuItem.getName() + " price: " + menuItem.getPrice() + "\n");
            }
        }
        for (Map.Entry<String, MenuItem> entry : menuC.getMenuItems().entrySet()) {
            System.out.print("--name: " + entry.getValue().getName() + " price: " + entry.getValue().getPrice() + "\n");
        }
    }
}

public class IteratorDemo {
    public static void main(String[] args) {
        MenuA menuA = new MenuA();
        MenuB menuB = new MenuB();
        MenuC menuC = new MenuC();
        Waitress waitress = new Waitress(menuA, menuB, menuC);
        // 使用Iterator
        waitress.printMenu();
        // 不使用Iterator
        waitress.printMenuWithoutIterator();
    }
}

class MenuBIterator implements Iterator<MenuItem> {
    private MenuItem[] list;
    private int position = 0;

    public MenuBIterator(MenuItem[] list) { this.list = list; }

    public MenuItem next() {
        MenuItem menuItem = list[position];
        position = position + 1;
        return menuItem;
    }

    public boolean hasNext() { return  !(position >= list.length || list[position] == null); }

    public void remove() {
        if (position <= 0) { throw new IllegalStateException("You can't remove an item until you've done at least one next()"); }
        if (list[position-1] != null) {
            for (int i = position-1; i < (list.length-1); i++) {
                list[i] = list[i+1];
            }
            list[list.length-1] = null;
        }
    }
}
