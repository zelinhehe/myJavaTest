import java.util.*;
import java.nio.file.Paths;
import java.io.IOException;

public class File
{
  public static void main(String[] args) throws IOException
  {
    Scanner in = new Scanner(Paths.get("myfile.txt"), "UTF-8");
    System.out.println(in.nextLine());
    System.out.println(in.nextLine());
    System.out.println(in.nextLine());
  }
}
