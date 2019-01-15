package mytest;

public class EnumTest {

    public static void main(String[] args){
        Size size = Size.SMALL;
        switch (size){
            case LARGE:
                System.out.println("large"); break;
            case SMALL:
                System.out.println("small"); break;
            case MEDIUM:
                System.out.println("medium"); break;
            default:
                System.out.println("default");
        }
        System.out.println(Size.valueOf("LARGE") == Size.LARGE);
        for (Size s : Size.values())
            System.out.println(s);
    }
}

enum Size {
    SMALL, MEDIUM, LARGE
}

enum Size1 {
    SMALL("S", "小号"),
    MEDIUM("M", "中号"),
    LARGE("L", "大号");

    private String abbr;
    private String title;
    private Size1(String abbr, String title){
        this.abbr = abbr;
        this.title = title;
    }
    public String getAbbr() {
        return abbr;
    }
    public String getTitle(){
        return title;
    }
    public static Size1 fromAbbr(String abbr){
        for (Size1 size1 : Size1.values()) {
            if (size1.getAbbr().equals(abbr))
                return size1;
        }
        return null;
    }
}
