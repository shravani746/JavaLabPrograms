import java.util.Scanner;

abstract class Employee {
    int id;
    String name;
    double basicSalary;
    static int count = 0; // Initialize static count
    final String companyName = "Hinge Health";

    Employee() {}

    Employee(int id, String name, double basicSalary) {
        this.id = id;
        this.name = name;
        this.basicSalary = basicSalary;
        count++;
    }

    // Abstract method
    abstract double calculateSalary();

    void displayDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Company Name: " + companyName);
    }
}

class Manager extends Employee {
    int teamSize;
    double bonus;

    Manager(int id, String name, double basicSalary, int teamSize, double bonus) {
        super(id, name, basicSalary);
        this.teamSize = teamSize;
        this.bonus = bonus;
    }

    @Override
    double calculateSalary() {
        return basicSalary + bonus + (teamSize * 1000);
    }

    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Team Size: " + teamSize);
        System.out.println("Bonus: " + bonus);
        System.out.println("Manager Total Salary: " + calculateSalary());
    }
}

class Developer extends Employee {
    int projectsWorked, performanceRating;

    Developer(int id, String name, double basicSalary, int projectsWorked, int performanceRating) {
        super(id, name, basicSalary);
        this.projectsWorked = projectsWorked;
        this.performanceRating = performanceRating;
    }

    @Override
    double calculateSalary() {
        return basicSalary + (projectsWorked * 500) + (performanceRating * 100);
    }

    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Projects Worked: " + projectsWorked);
        System.out.println("Performance Rating: " + performanceRating);
        System.out.println("Developer Total Salary: " + calculateSalary());
    }
}

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Array of employees
        Employee emp[] = new Employee[3];

        // Input for Manager
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

        // Input for Developers
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

        // Display Details
        for (Employee employee : emp) {
            employee.displayDetails();
            System.out.println("-------------------------");
        }

        // Display total employees
        System.out.println("Total Employees: " + Employee.count);
        System.out.println("Company Name: " + emp[0].companyName);

        scanner.close();
    }
}
