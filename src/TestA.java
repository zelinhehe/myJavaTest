public class TestA {
    public static void main(String[] args) {
        AnyThing a = new Moon(new Dream(new You(null)));
        a.exe();
    }
}

interface AnyThing {
    void exe();
}

class Moon implements AnyThing {
    private AnyThing a;
    public Moon(AnyThing a) {
        this.a = a;
    }
    public void exe() {
        System.out.print("明月装饰了");
        a.exe();
    }
}

class Dream implements AnyThing {
    private AnyThing a;
    public Dream(AnyThing a) {
        this.a=a;
    }
    public void exe() {
        System.out.print("梦装饰了");
        a.exe();
    }
}

class You implements AnyThing {
    private AnyThing a;
    public You(AnyThing a) {
        this.a = a;
    }
    public void exe() {
        System.out.print("你");
    }
}
