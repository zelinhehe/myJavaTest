
public class ParamTest
{
  public static void main(String[] args)
  {
    System.out.println("Testing tripleValue:");
    double percent = 10;
    System.out.println("Before: percent=" + percent);
    tripleValue(percent);
    System.out.println("After: percent=" + percent);

    System.out.println("\nTesting tripleSalary:");
    Employee harry = new Employee("Harry", 50000);
    System.out.println("Before: salary=" + harry.getSalary());
    tripleSalary(harry);
    System.out.println("After: salary=" + harry.getSalary());

    System.out.println("\nTesting sqap:");
    Employee a = new Employee("Alice", 70000);
    Employee b = new Employee("Bob", 80000);
    System.out.println("a: " + a.getName() + " b: " + b.getName());
    swap(a, b);
    System.out.println("a: " + a.getName() + " b: " + b.getName());
  }

  public static void tripleValue(double x)
  {
    x = 3 * x;
    System.out.println("End of method: x=" + x);
  }

  public static void tripleSalary(Employee x)
  {
    x.raiseSalary(200);
    System.out.println("End of method: salary=" + x.getSalary());
  }

  public static void swap(Employee x, Employee y)
  {
    Employee temp = x;
    x = y;
    y = temp;
    System.out.println("End of method: x=" + x.getName() + " y=" + y.getName());
  }
}

class Employee
{
  private String name;
  private double salary;

  public Employee(String n, double s)
  {
    name = n;
    salary = s;
  }

  public String getName()
  {
    return name;
  }

  public double getSalary()
  {
    return salary;
  }

  public void raiseSalary(double byPercent)
  {
    double raise = salary * byPercent / 100;
    salary = salary + raise;
  }
}
