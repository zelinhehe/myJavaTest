package DesignPattern.Factory.AbstractFactory;

// ------ 原料（产品） ------
interface Sauce { String toString();}  // 调料接口
interface Cheese { String toString();}  // 奶酪接口
class ASauce implements Sauce { public String toString() { return "A Sauce"; }}  // 调料A
class BSauce implements Sauce { public String toString() { return "B Sauce"; }}  // 调料B
class ACheese implements Cheese { public String toString() { return "A Cheese"; }}  // 奶酪A
class BCheese implements Cheese { public String toString() { return "B Cheese"; }}  // 奶酪B

// ------ 原料工厂（创建产品对象） ------
// 原料工厂接口，提供调料和奶酪
interface IngredientFactory {
    Sauce createSauce();
    Cheese createCheese();
}
// 南方口味的原料工厂
class SouthIngredientFactory implements IngredientFactory {
    public Sauce createSauce() { return new ASauce(); }
    public Cheese createCheese() { return new ACheese(); }
}
// 北方口味的原料工厂
class NouthIngredientFactory implements IngredientFactory {
    public Sauce createSauce() { return new BSauce(); }
    public Cheese createCheese() { return new BCheese(); }
}
// ------ 披萨 ------
// 抽象类：披萨
abstract class Pizza {
    String name;
    Sauce sauce;
    Cheese cheese;

    abstract void prepare();

    void bake() { System.out.println("Baking"); }
    void box() { System.out.println("Boxing"); }
    void setName(String name) { this.name = name; }
    String getName() { return name; }
    public String toString() { return " [ " + name + " ] " + sauce + " | " + cheese; }
}
// X型披萨
class XPizza extends Pizza {
    private IngredientFactory ingredientFactory;

    public XPizza(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    void prepare() {
        System.out.println("Preparing " + name);
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
    }
}
// Y型披萨
class YPizza extends Pizza {
    private IngredientFactory ingredientFactory;

    public YPizza(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    void prepare() {
        System.out.println("Preparing " + name);
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
    }
}
// ------ 披萨店 ------
// 抽象类：披萨店
abstract class PizzaStore {

    protected abstract Pizza createPizza(String item);

    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);
        System.out.println("--- Making a " + pizza.getName() + " ---");
        pizza.prepare();
        pizza.bake();
        pizza.box();
        return pizza;
    }
}
// 披萨店：北京
class BeijingPizzaStore extends PizzaStore {

    protected Pizza createPizza(String item) {
        Pizza pizza = null;
        IngredientFactory ingredientFactory = new NouthIngredientFactory();

        if (item.equals("X")) {
            pizza = new XPizza(ingredientFactory);
            pizza.setName("Beijing Style X Pizza");
        } else if (item.equals("Y")) {
            pizza = new YPizza(ingredientFactory);
            pizza.setName("Beijing Style Y Pizza");
        }
        return pizza;
    }
}
// 披萨店：杭州
class HangzhouPizzaStore extends PizzaStore {

    protected Pizza createPizza(String item) {
        Pizza pizza = null;
        IngredientFactory ingredientFactory = new SouthIngredientFactory();

        if (item.equals("X")) {
            pizza = new XPizza(ingredientFactory);
            pizza.setName("Hangzhou Style Cheese Pizza");
        } else if (item.equals("Y")) {
            pizza = new YPizza(ingredientFactory);
            pizza.setName("Hangzhou Style Veggie Pizza");
        }
        return pizza;
    }
}

public class AbstractFactoryDemo {
    public static void main(String[] args) {
        PizzaStore beiJingStore = new BeijingPizzaStore();

        Pizza pizza = beiJingStore.orderPizza("X");
        System.out.println("ordered a " + pizza + "\n");

        pizza = beiJingStore.orderPizza("Y");
        System.out.println("ordered a " + pizza + "\n");

        PizzaStore hangZhouStore = new HangzhouPizzaStore();

        pizza = hangZhouStore.orderPizza("X");
        System.out.println("ordered a " + pizza + "\n");

        pizza = hangZhouStore.orderPizza("Y");
        System.out.println("ordered a " + pizza + "\n");
    }
}
