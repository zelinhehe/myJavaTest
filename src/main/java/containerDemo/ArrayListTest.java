package containerDemo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListTest
{
  public static void main(String[] args)
  {
//    Employee[] staff = new Employee[3];

//    staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
//    staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
//    staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

	ArrayList<Employee> staff = new ArrayList<>();
	
	staff.add(new Employee("Carl Cracker", 75000, 1987, 12, 15));
	staff.add(new Employee("Harry Hacker", 50000, 1989, 10, 1));
	staff.add(new Employee("Tony Tester", 40000, 1990, 3, 15));

    for (Employee e: staff)
        e.raiseSalary(5);

    for (Employee e: staff)
        System.out.println("name: " + e.getName() + " salary: " + e.getSalary() + 
                           " hireDay: " + e.getHireDay());
    
    ArrayList<Employee> staff2 = new ArrayList<>(3);
    staff2.add(new Employee("b", 2, 1999, 2, 2));
    staff2.set(0, new Employee("a", 1, 1999, 1, 1));
    Employee ele = staff2.get(0);
    System.out.println(ele.getName());
    for (Employee e: staff2)
    	System.out.println(e);
    
    
    Employee[] staff_array = new Employee[staff.size()];
    staff.toArray(staff_array);
    System.out.println(staff_array[1].getName());

    Integer[] a = {1,2,3,4};
    List<Integer> list = Arrays.asList(a);
    System.out.println(list);
    a[3] = 5;
    System.out.println(list);
  }
}

class Employee
{
    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(String n, double s, int year, int month, int day)
    {
        name = n;
        salary = s;
        hireDay = LocalDate.of(year, month, day);
    }

    public String getName()
    {
        return name;
    }

    public double getSalary()
    {
        return salary;
    }

    public LocalDate getHireDay()
    {
        return hireDay;
    }

    public void raiseSalary(double byPercent)
    {
        double raise = salary * byPercent / 100;
        salary += raise;
    }
}
