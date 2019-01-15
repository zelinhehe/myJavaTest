public class Test
{
    public static void main(String[] arg)
    {
        Test test = new Test();
        double x = 9.97;
        System.out.println(Math.round(x));
        System.out.println(test.judgeType(Math.round(x)));
    }

    public String judgeType(Object temp)
    {
        if (temp instanceof Double)
            System.out.println("double");
        else if (temp instanceof Integer)
            System.out.println("int");
        else if (temp instanceof Long)
            System.out.println("long");
        else
            System.out.println("other type");
        return "value";
    }
}
