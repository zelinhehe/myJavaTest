package DesignPattern.Decorator;

// 被装饰对象（饮料）抽象类
abstract class Beverage {
    String description = "Unknown Beverage";

    public String getDescription() { return description; }

    public abstract double cost();
}
// 装饰器（调料）抽象类
abstract class CondimentDecorator extends Beverage {
    Beverage beverage;
    public abstract String getDescription();
}

// ---------- 被装饰对象（饮料）---------------

// 饮料：烘焙咖啡
class DarkRoast extends Beverage {
    public DarkRoast() { description = "DarkRoast"; }

    @Override
    public double cost() { return 1.00; }
}
// 饮料：浓咖啡
class Espresso extends Beverage {
    public Espresso() { description = "Espresso"; }

    @Override
    public double cost() { return 2.00; }
}

// ----------- 装饰器（调料）--------------

// 调料：牛奶
class Milk extends CondimentDecorator {
    public Milk(Beverage beverage) { this.beverage = beverage; }

    @Override
    public double cost() { return beverage.cost() + .10; }

    @Override
    public String getDescription() { return beverage.getDescription() + ", Milk"; }
}
// 调料：摩卡
class Mocha extends CondimentDecorator {
    public Mocha(Beverage beverage) { this.beverage = beverage; }

    @Override
    public double cost() { return beverage.cost() + .20; }

    @Override
    public String getDescription() { return beverage.getDescription() + ", Mocha"; }
}

public class DecoratorDemo {
    public static void main(String[] args) {
        // 来一杯浓咖啡，不加调料
        Beverage beverage = new Espresso();
        System.out.println(beverage.getDescription() + " $" + beverage.cost());

        // 来一杯烘焙咖啡，加两份摩卡，一份牛奶
        Beverage beverage1 = new DarkRoast();
        beverage1 = new Mocha(beverage1);
        beverage1 = new Mocha(beverage1);
        beverage1 = new Milk(beverage1);
        System.out.println(beverage1.getDescription() + " $" + beverage1.cost());
    }
}
