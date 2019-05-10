package a_DesignPatterns.Factory.SimpleFactory;

/**
 * SimpleFactory 简单工厂模式
 */

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

class CheesePizza extends Pizza {
    public CheesePizza() {
        name = "Cheese Pizza";
        sauce = "Sauce1";
    }
}

class VeggiePizza extends Pizza {
    public VeggiePizza() {
        name = "Veggie Pizza";
        sauce = "Sauce2";
    }
}

class SimplePizzaFactory {
    public Pizza createPizza(String type) {
        Pizza pizza = null;
        if (type.equals("cheese")) {
            pizza = new CheesePizza();
        } else if (type.equals("veggie")) {
            pizza = new VeggiePizza();
        }
        return pizza;
    }
}

class PizzaStore {
    SimplePizzaFactory factory;

    public PizzaStore(SimplePizzaFactory factory) { this.factory = factory; }

    public Pizza orderPizza(String type) {
        // 生成各种type的pizza对象交给专门的SimpleFactory处理
        Pizza pizza = factory.createPizza(type);

        pizza.bake();
        pizza.box();
        return pizza;
    }
}

public class SimpleFactoryDemo {

    public static void main(String[] args) {
        SimplePizzaFactory factory = new SimplePizzaFactory();
        PizzaStore store = new PizzaStore(factory);

        Pizza pizza = store.orderPizza("cheese");
        System.out.println(pizza);

        pizza = store.orderPizza("veggie");
        System.out.println(pizza);
    }
}
