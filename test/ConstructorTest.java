import java.util.*;

public class ConstructorTest
{
  public static void main(String[] args)
  {
    Employee1[] staff = new Employee1[3];
    System.out.println("new Harry 40000");
    staff[0] = new Employee1("Harry", 40000);
    System.out.println("new 60000");
    staff[1] = new Employee1(60000);
    System.out.println("new ");
    staff[2] = new Employee1();
    
    for (Employee1 e: staff)
        System.out.println("name: " + e.getName() + " id: " + e.getId() + " salary: " + e.getSalary());
  }
}

class Employee1
{
  private static int nextId;

  private int id;
  private String name = "";  // instance field initialization
  private double salary;

  // static initialization block
  static
  {
	System.out.println("static initialization block");
    Random generator = new Random();
    // set nextId to a random number between 0 and 9999
    nextId = generator.nextInt(10000);
  }

  // object initialization block
  {
	System.out.println("object initialization block");
    id = nextId;
    nextId++;
  }

  // three overloading constructors
  public Employee1(String n, double s)
  {
    name = n;
    salary = s;
  }
  public Employee1(double s)
  {
    // calls the Employee(String, double) constructor
    this("Employee #" + nextId, s);
  }
  public Employee1()
  {
    // name initialized to ""
    // salary not explicitly set
    // id initialized in initization block
  }

  public String getName()
  {
    return name;
  }

  public double getSalary()
  {
    return salary;
  }

  public int getId()
  {
    return id;
  }
}
