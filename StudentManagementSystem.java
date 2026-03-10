import java.util.Arrays;

// Base Class: Person
class Person {
    protected String name;
    protected int age;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Display method
    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

// Subclass: Student (Inherits from Person)
class Student extends Person {
    private int rollNumber;
    private double[] marks; // Array to store marks
    private static int studentCount = 0; // Static field

    // Constructor with this keyword
    public Student(String name, int age, int rollNumber, double[] marks) {
        super(name, age); // Call to superclass constructor
        this.rollNumber = rollNumber;
        this.marks = marks;
        studentCount++; // Increment student count
    }

    // Method to calculate total marks
    public double calculateTotalMarks() {
        double total = 0;
        for (double mark : marks) { // Using a for-each loop
            total += mark;
        }
        return total;
    }

    // Method to calculate average marks
    public double calculateAverage() {
        return calculateTotalMarks() / marks.length;
    }

    // Overriding the display method
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Marks: " + Arrays.toString(marks));
        System.out.println("Total Marks: " + calculateTotalMarks());
        System.out.println("Average Marks: " + calculateAverage());
    }

    // Static method to get student count
    public static int getStudentCount() {
        return studentCount;
    }
}

// Main Class
public class StudentManagementSystem {
    public static void main(String[] args) {
        // Command-line parameters to pass student count
        if (args.length == 0) {
            System.out.println("Usage: java StudentManagementSystem <number_of_students>");
            return;
        }

        int numStudents = Integer.parseInt(args[0]);
        Student[] students = new Student[numStudents]; // Array of Student objects

        // Initialize student data
        for (int i = 0; i < numStudents; i++) {
            System.out.println("\nEnter details for Student " + (i + 1) + ":");
            String name = "Student" + (i + 1); // Example name
            int age = 18 + i; // Example age
            int rollNumber = 1001 + i; // Example roll number
            double[] marks = {85 + i, 90 + i, 88 + i}; // Example marks
            students[i] = new Student(name, age, rollNumber, marks); // Using parameterized constructor
        }

        // Sort students by total marks using a custom comparator (Array Sorting)
        Arrays.sort(students, (s1, s2) -> Double.compare(s2.calculateTotalMarks(), s1.calculateTotalMarks()));

        // Display sorted student data
        System.out.println("\n--- Sorted Student Data by Total Marks ---");
        for (Student student : students) {
            student.displayInfo();
        }

        // Accessing static method
        System.out.println("\nTotal Students Created: " + Student.getStudentCount());
    }
}
