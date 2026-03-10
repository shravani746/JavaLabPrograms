import java.util.Scanner;

class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class Student {
    private String name;
    private int age;
    private String studentID;
    private int marks;

    public Student(String name, int age, String studentID, int marks) throws InvalidAgeException, InvalidMarksException {
        if (age <= 0 || age > 50) {
            throw new InvalidAgeException("Age must be between 1 and 150.");
        }
        if (marks < 0 || marks > 100) {
            throw new InvalidMarksException("Marks must be between 0 and 100.");
        }
        this.name = name;
        this.age = age;
        this.studentID = studentID;
        this.marks = marks;
    }

    public void display() {
        System.out.println("\nStudent Details:");
        System.out.println("Student ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Marks: " + marks);
    }
}

public class StudentManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Student[] students = new Student[3];
        int Count = 0;

        while (Count < students.length) {
            try {
                System.out.println("\nEnter details for student " + (Count + 1) + ":");

                System.out.print("Enter Name: ");
                String name;
                while (true) {
                    name = scanner.nextLine().trim();
                    if (!name.isEmpty()) {
                        break;
                    } else {
                        System.out.print("Name cannot be empty. Enter again: ");
                    }
                }

                System.out.print("Enter Age: ");
                int age;
                         age = scanner.nextInt();
                         scanner.nextLine(); 
               
                System.out.print("Enter Student ID: ");
                String studentID;
                while (true) {
                    studentID = scanner.nextLine().trim();
                    if (!studentID.isEmpty()) {
                        break;
                    } else {
                        System.out.print("ID cannot be empty. Enter again: ");
                    }
                }

                System.out.print("Enter Marks: ");
                int marks;
                         marks = scanner.nextInt();
                         scanner.nextLine(); 

                students[Count] = new Student(name, age, studentID, marks);
                Count++;
            } catch (InvalidAgeException | InvalidMarksException e) {
                System.out.println("Error: " + e.getMessage());
            } 
        }

        System.out.println("\n--- Student List ---");
        for (Student student : students) {
            student.display();
        }
        scanner.close();
    }
}
