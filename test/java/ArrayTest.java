import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayTest
{
    public static void main(String[] args)
    {
        int[] a = new int[10];
        for (int i=0; i<a.length; i++)
            a[i] = i;
        for (int i=0; i<a.length; i++)
            System.out.print(a[i]);
        
        String[] names = new String[10];
        for (int i=0; i<names.length; i++) names[i] = "a";
        for (int i=0; i<names.length; i++)
            System.out.print(names[i]);
        for (String i: names)
            System.out.print(i);

        System.out.println(Arrays.toString(a));

        int[] smallPrimes = {2, 3, 5, 7, 11, 13};
        smallPrimes = new int[] {17, 19, 23, 29, 31, 37};
        System.out.println(Arrays.toString(smallPrimes));

        int[] luckyNumbers = smallPrimes;
        luckyNumbers[5] = 12;
        System.out.println(Arrays.toString(smallPrimes));
        System.out.println(Arrays.toString(luckyNumbers));
        int[] copiedLuckyNumbers = Arrays.copyOf(luckyNumbers, luckyNumbers.length);
        System.out.println(Arrays.toString(copiedLuckyNumbers));

        Integer integer = 3;
        f(integer);
        System.out.println(integer);

        String s = "abc";
        f(s);
        System.out.println(s);

        List<String> list = new ArrayList<>(Arrays.asList("abc", "b"));
        f(list);
        System.out.println(list);
    }

    public static void f(Integer i) {
        i += 1;
    }

    public static void f(String s) {
        s += "def";
    }

    public static void f(List<String> list) {
        list.add("cde");
    }
}
