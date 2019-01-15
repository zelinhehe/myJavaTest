public class StringTest
{
    public static void main(String[] args)
    {
        String s = "abcdefg";
        String s1 = s.substring(2, 4);
        System.out.println("s.substring: " + s1);

        int i = 12345;
        String s2 = s1 + i;
        System.out.println(s2);
        
        String all = String.join(",", "a", "b", "c");
        System.out.println(all);

        // s:"abcdefg" => "abcd---"
        s = s.substring(0, 4) + "---";
        System.out.println(s);

        s = "abcdefg";
        System.out.println("s.equals: " + "abcdefg".equals(s));
        System.out.println("s.equals: " + "ABCdefg".equalsIgnoreCase(s));

        StringBuilder builder = new StringBuilder();
        builder.append('a');
        builder.append("bcd");
        System.out.println("builder: " + builder + " length:" + builder.length());
        builder.insert(3, "xyz");
        System.out.println(builder);
        builder.delete(0, 1);
        System.out.println(builder);
        String b = builder.toString();
        System.out.println(b);
    }

}
