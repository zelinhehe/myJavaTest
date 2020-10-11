package DesignPattern.Composite;

import java.util.ArrayList;

/**
 * 菜单具有包含关系，菜单里既有菜单项，也可能还有另一个菜单——树形结构
 * 菜单项和菜单都看成（继承）MenuComponent
 * 每个 Component都有 print 方法：
 *      菜单项的 print 会打印自己 name，price
 *      菜单的 print 会调用每个子节点的print（子节点可能是菜单项，也可能是菜单，如果是菜单，那么它的print会继续开始新的遍历，直到所有子节点都是菜单项）
 *
 * 服务员waitress直接调用传给她的菜单，然后调用菜单的 print，完成此菜单及其子菜单的遍历
 * 新需求：要求服务员对菜单做一些过滤，那么就需要服务员直接遍历所有菜单，可加入迭代器模式，具体实现见CompositeIteratorDemo
 */
// 抽象类-菜单组件
abstract class MenuComponent {
    public void add(MenuComponent menuComponent) { throw new UnsupportedOperationException(); }
    public void remove(MenuComponent menuComponent) { throw new UnsupportedOperationException(); }
    public MenuComponent getChild(int i) { throw new UnsupportedOperationException(); }

    public String getName() { throw new UnsupportedOperationException(); }
    public double getPrice() { throw new UnsupportedOperationException(); }

    public void print() { throw new UnsupportedOperationException(); }
}
// 菜单项
class MenuItem extends MenuComponent {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public void print() { System.out.println(getName() + ", " + getPrice()); }
}
// 菜单
class Menu extends MenuComponent {
    private ArrayList<MenuComponent> menuComponents = new ArrayList<>();
    private String name;

    public Menu(String name) { this.name = name; }

    public void add(MenuComponent menuComponent) { menuComponents.add(menuComponent); }
    public void remove(MenuComponent menuComponent) { menuComponents.remove(menuComponent); }
    public MenuComponent getChild(int i) { return menuComponents.get(i); }

    public String getName() { return name; }

    public void print() {
        System.out.println("--- " + getName() + " --- ");
        for (MenuComponent component : menuComponents) { component.print(); }
//        Iterator<MenuComponent> iterator = menuComponents.iterator();
//        while (iterator.hasNext()) {
//            MenuComponent menuComponent = iterator.next();
//            menuComponent.print();
//        }
    }
}
// 服务员，管理菜单
class Waitress {
    private MenuComponent menuRoot;

    public Waitress(MenuComponent menuRoot) { this.menuRoot = menuRoot; }
    public void printMenu() { menuRoot.print(); }
}
public class CompositeDemo {
    public static void main(String[] args) {
        MenuComponent menuRoot = new Menu("ALL MENUS");
        MenuComponent menuA = new Menu("menuA");
        MenuComponent menuA1 = new Menu("menuA1 ");
        MenuComponent menuB = new Menu("menuB");
        MenuComponent menuB1 = new Menu("menuB1");
        MenuComponent menuB11 = new Menu("menuB11");

        menuA.add(new MenuItem("a1", 2.99));
        menuA.add(new MenuItem("a2", 2.99));
        menuA.add(new MenuItem("a3", 3.49));

        menuA1.add(new MenuItem("a1-1", 2.99));
        menuA1.add(new MenuItem("a1-2", 3.29));

        menuB.add(new MenuItem("b1", 3.59));
        menuB.add(new MenuItem("b2", 2.99));

        menuB1.add(new MenuItem("b1-1", 2.99));
        menuB1.add(new MenuItem("b1-2", 3.29));

        menuB11.add(new MenuItem("b1-1-1", 2.99));
        menuB11.add(new MenuItem("b1-1-2", 3.29));

        menuRoot.add(menuA);
        menuRoot.add(menuB);
        menuA.add(menuA1);
        menuB.add(menuB1);
        menuB1.add(menuB11);

        System.out.println("传入菜单 menuRoot");
        Waitress waitress = new Waitress(menuRoot);
        waitress.printMenu();
        System.out.println("传入菜单 menuB1");
        waitress = new Waitress(menuB1);
        waitress.printMenu();
    }
}
