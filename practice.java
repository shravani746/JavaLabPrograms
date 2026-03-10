import java.util.Scanner;

class Employee
{
    int id;
    String name;
    double basicsalary;
    static int count;
    final String companyName="Hinge Health";

    Employee(){}

    Employee(int id, String name, double basicsalary)
    {
        this.id=id;
        this.name=name;
        this.basicsalary=basicsalary;
        count++;
    }

    abstract double calculateSalary();

    void displayDetails()
    {
        System.out.println("id : " + id);
        System.out.println("name : " + name);
        System.out.println("basic salary : " + basicsalary);
        System.out.println("company name : " + companyName);
    }
}

class Manager extends Employee{
    int teamSize;
    double bonus;

    Manager(int id, String name, double basicsalary,int teamSize, double bonus){
        super(id,name,basicsalary);
        this.teamSize=teamSize;
        this.bonus=bonus;
    }

    @Override
    double calculateSalary(){
       return super.basicsalary + bonus + (teamSize * 1000);
    }

    @Override
    void displayDetails(){
        super.displayDetails();
        System.out.println("teamSize : " + teamSize);
        System.out.println("bonus : " + bonus);
        System.out.println("Manager salary after bonus : " + calculateSalary());
    }
}

class Developer extends Employee{
    int projectWorked, performanceRating;

    Developer(int id, String name, double basicsalary,int projectWorked, int performanceRating){
        super(id,name,basicsalary);
        this.projectWorked=projectWorked;
        this.performanceRating=performanceRating;
    }

    @Override
    double calculateSalary(){
      return super.basicsalary + (projectWorked * 500) + (performanceRating * 100);
    }

    @Override
    void displayDetails(){
        super.displayDetails();
        System.out.println("projectWorked : " + projectWorked);
        System.out.println("performanceRating : " + performanceRating);
        System.out.println("Developer salary after projects and rating : " + calculateSalary());
    }

}

public class EmployeeManagementSystem{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        Employee emp[] = new Employee[3];

        
        System.out.println("Enter details for Manager:");
        System.out.print("ID: ");
        int managerId = scanner.nextInt();
        System.out.print("Name: ");
        scanner.nextLine(); // Consume newline
        String managerName = scanner.nextLine();
        System.out.print("Basic Salary: ");
        double managerSalary = scanner.nextDouble();
        System.out.print("Team Size: ");
        int teamSize = scanner.nextInt();
        System.out.print("Bonus: ");
        double bonus = scanner.nextDouble();
        emp[0] = new Manager(managerId, managerName, managerSalary, teamSize, bonus);

        // Create Developer objects
        for (int i = 1; i < 3; i++) {
            System.out.println("Enter details for Developer " + i + ":");
            System.out.print("ID: ");
            int devId = scanner.nextInt();
            System.out.print("Name: ");
            scanner.nextLine(); // Consume newline
            String devName = scanner.nextLine();
            System.out.print("Basic Salary: ");
            double devSalary = scanner.nextDouble();
            System.out.print("Projects Worked: ");
            int projectsWorked = scanner.nextInt();
            System.out.print("Performance Rating: ");
            int performanceRating = scanner.nextInt();
            emp[i] = new Developer(devId, devName, devSalary, projectsWorked, performanceRating);
        }

        for(Employee employee : emp)
        {
            employee.displayDetails();
            System.out.println("-------------------------");
        }

        System.out.println("Total Employees: " + Employee.count);
        System.out.println("Company Name: " + emp[0].companyName);

        sc.close();
    } 
} 