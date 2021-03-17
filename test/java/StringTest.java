public class StringTest {

    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer("abc");
        stringBuffer.append("def");
        String s = stringBuffer.toString();
        System.out.println(s);

        StringBuilder stringBuilder = new StringBuilder("abc");
        stringBuilder.append("def");
        String s1 = stringBuilder.toString();
        System.out.println(s1);

        String s2 = "abc";
        String s3 = s2.concat("def");
        System.out.println(s3);
    }
}
