package DesignPattern.Factory.FactoryMethod;

/**
 *
 */
// 披萨抽象类
abstract class Pizza {
    protected String name;
    protected String sauce;

    public String getName() { return name; }
    public void bake() { System.out.println("Baking " + name); }
    public void box() { System.out.println("Boxing " + name ); }

    public String toString() {
        return "[ " + name + " ] <sauce>: " + sauce;
    }
}
// 纽约风味奶酪披萨
class NYStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza() {
        name = "NY Style Cheese Pizza";
        sauce = "Sauce1";
    }
}
// 纽约风味蔬菜披萨
class NYStyleVeggiePizza extends Pizza {
    public NYStyleVeggiePizza() {
        name = "NY Style Veggie Pizza";
        sauce = "Sauce2";
    }
}
// 芝加哥风味奶酪披萨
class ChicagoStyleCheesePizza extends Pizza {
    public ChicagoStyleCheesePizza() {
        name = "Chicago Style Cheese Pizza";
        sauce = "Sauce11";
    }
    // 特殊包装
    public void box() {System.out.println("Chicago Box");}
}
// 芝加哥风味蔬菜披萨
class ChicagoStyleVeggiePizza extends Pizza {
    public ChicagoStyleVeggiePizza() {
        name = "Chicago Style Veggie Pizza";
        sauce = "Sauce22";
    }
}

// 披萨店抽象类
abstract class PizzaStore {
    // 工厂方法
    abstract Pizza createPizza(String item);

    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);

        pizza.bake();
        pizza.box();
        return pizza;
    }
}
// 纽约风味披萨店
class NYPizzaStore extends PizzaStore {
    @Override
    Pizza createPizza(String item) {
        Pizza pizza = null;
        if (item.equals("cheese")) {
            pizza = new NYStyleCheesePizza();
        } else if (item.equals("veggie")) {
            pizza = new NYStyleVeggiePizza();
        }
        return pizza;
    }
}
// 芝加哥风味披萨店
class ChicagoPizzaStore extends PizzaStore {
    @Override
    Pizza createPizza(String item) {
        Pizza pizza = null;
        if (item.equals("cheese")) {
            pizza = new ChicagoStyleCheesePizza();
        } else if (item.equals("veggie")) {
            pizza = new ChicagoStyleVeggiePizza();
        }
        return pizza;
    }
}

public class FactoryMethodDemo {

    public static void main(String[] args) {
        PizzaStore nyStore = new NYPizzaStore();

        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println(pizza);
        pizza = nyStore.orderPizza("veggie");
        System.out.println(pizza);

        PizzaStore chicagoStore = new ChicagoPizzaStore();

        pizza = chicagoStore.orderPizza("cheese");
        System.out.println(pizza);
        pizza = chicagoStore.orderPizza("veggie");
        System.out.println(pizza);
    }
}
