import java.util.Arrays;

public class MainArgs
{
  public static void main(String[] args)
  {
    System.out.println(Arrays.toString(args));
    if (args.length == 0 || args[0].equals("-h"))
        System.out.print("Hello,");
    else if (args[0].equals("-g"))
        System.out.print("Goodbye,");
    for (int i=1; i<args.length; i++)
        System.out.print(" " + args[i]);
    System.out.println("!");
  }
}
