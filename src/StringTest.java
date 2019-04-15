import java.util.Arrays;

public class StringTest {

    public static void main(String[] args) {
//        stringTest();
//        stringBuilderTest();
        arraysTest();
    }

    public static void stringTest() {
        String name = "老马";
        name += "说编程 aB,aB ";
        System.out.println(name);

        System.out.println(name.indexOf("马"));
        System.out.println(name.isEmpty());
        System.out.println(name.length());
        System.out.println(name.substring(2));
        System.out.println(name.substring(2, 4));
        System.out.println(name.contains("编程"));
        System.out.println(name.startsWith("老马"));
        System.out.println(name.startsWith("说", 2));
        System.out.println(name.equals("老马说编程"));
        System.out.println(name.toUpperCase());
        System.out.println(name.toLowerCase());
        System.out.println(name.concat("efg"));
        System.out.println(name.replace("aB", "AA"));
        System.out.println(name.trim());
        String[] arr = name.split(",");
        for (String s : arr)
            System.out.println("s: " + s);

        char[] s1 = {1,2,3,4};
        name.getChars(1, 3, s1, 2);
        System.out.println(Arrays.toString(s1));
    }

    public static void stringBuilderTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("老马说编程");
        sb.append("，探索编程本质");
        System.out.println(sb.toString());
    }

    public static void arraysTest() {
        int[] arr = {9, 8, 4, 3};
        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        String[] arrStr = {"hello", "world", "Break", "abc"};
        Arrays.sort(arrStr);
//        Arrays.sort(arrStr, String::compareToIgnoreCase);
//        Arrays.sort(arrStr, String.CASE_INSENSITIVE_ORDER);
        System.out.println(Arrays.toString(arrStr));
    }
}
