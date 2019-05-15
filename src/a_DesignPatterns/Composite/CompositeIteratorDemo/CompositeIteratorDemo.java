package a_DesignPatterns.Composite.CompositeIteratorDemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * 菜单具有包含关系，菜单里既有菜单项，也可能还有另一个菜单——树形结构
 * 菜单项和菜单都看成（继承）MenuComponent
 * 每个 Component都有 print 方法：
 *      菜单项的 print 会打印自己 name，price
 *      菜单的 print 会调用每个子节点的print（子节点可能是菜单项，也可能是菜单，如果是菜单，那么它的print会继续开始新的遍历，直到所有子节点都是菜单项）
 *
 * 要求服务员对菜单做一些过滤，那么就需要服务员直接遍历所有菜单，然后根据条件过滤。此处使用组合模式+迭代器模式
 *      使用Iterator，在 Component中加入 createIterator
 */
// 抽象类-菜单组件
abstract class MenuComponent {
    public void add(MenuComponent menuComponent) { throw new UnsupportedOperationException(); }
    public void remove(MenuComponent menuComponent) { throw new UnsupportedOperationException(); }
    public MenuComponent getChild(int i) { throw new UnsupportedOperationException(); }

    public String getName() { throw new UnsupportedOperationException(); }
    public double getPrice() { throw new UnsupportedOperationException(); }

    public void print() { throw new UnsupportedOperationException(); }
    public abstract Iterator<MenuComponent> createIterator();
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

    public Iterator<MenuComponent> createIterator() {
        return new NullIterator();
    }
}

// 菜单
class Menu extends MenuComponent {
    private ArrayList<MenuComponent> menuComponents = new ArrayList<>();
    private String name;
    private Iterator<MenuComponent> iterator = null;

    public Menu(String name) { this.name = name; }

    public void add(MenuComponent menuComponent) { menuComponents.add(menuComponent); }
    public void remove(MenuComponent menuComponent) { menuComponents.remove(menuComponent); }
    public MenuComponent getChild(int i) { return menuComponents.get(i); }

    public String getName() { return name; }

    public void print() {
        System.out.println("--- " + getName() + " --- ");
//        for (MenuComponent component : menuComponents) { component.print(); }
        Iterator<MenuComponent> iterator = menuComponents.iterator();
        while (iterator.hasNext()) {
            MenuComponent menuComponent = iterator.next();
            menuComponent.print();
        }
    }
    public Iterator<MenuComponent> createIterator() {
        if (iterator == null) {
            iterator = new CompositeIterator(menuComponents.iterator());
        }
        return iterator;
    }
}

// 服务员，管理菜单
class Waitress {
    private MenuComponent menuRoot;

    public Waitress(MenuComponent menuRoot) { this.menuRoot = menuRoot; }
    public void printMenu() { menuRoot.print(); }
    public void printMenuFilter(Double price) {
        Iterator<MenuComponent> iterator = menuRoot.createIterator();
        System.out.println("\n大于价格：" + price + " 的食物\n");
        while (iterator.hasNext()) {
            MenuComponent menuComponent = iterator.next();
            try {
                if (menuComponent.getPrice() > price) {
                    menuComponent.print(); }
            } catch (UnsupportedOperationException e) { }
        }
    }
}

public class CompositeIteratorDemo {
    public static void main(String[] args) {
        MenuComponent menuRoot = new Menu("ALL MENUS");
        MenuComponent menuA = new Menu("menuA");
        MenuComponent menuA1 = new Menu("menuA1");
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

        Waitress waitress = new Waitress(menuRoot);
        waitress.printMenu();  // 遍历所有菜单
        waitress.printMenuFilter(3.00);  // 筛选价格大于3的食物
    }
}

class NullIterator implements Iterator<MenuComponent> {
    public MenuComponent next() { return null; }
    public boolean hasNext() { return false; }
}
class CompositeIterator implements Iterator<MenuComponent> {
    Stack<Iterator<MenuComponent>> stack = new Stack<>();

    public CompositeIterator(Iterator<MenuComponent> iterator) { stack.push(iterator); }

    public MenuComponent next() {
        if (hasNext()) {
            Iterator<MenuComponent> iterator = stack.peek();
            MenuComponent component = iterator.next();
            stack.push(component.createIterator());
            return component;
        } else {
            return null;
        }
    }

    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        } else {
            Iterator<MenuComponent> iterator = stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }
}
