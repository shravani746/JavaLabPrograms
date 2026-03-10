package studentinfo;

public class Student {
    private String name;
    private int rollNumber;
    private String course;
    private double gpa;

    public Student(String name, int rollNumber, String course, double gpa) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.course = course;
        this.gpa = gpa;
    }

    public String returnName() {
        return name;
    }

    public int returnRollNumber() {
        return rollNumber;
    }

    public String returnCourse() {
        return course;
    }

    public double returnGpa() {
        return gpa;
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Course: " + course);
        System.out.println("GPA: " + gpa);
        System.out.println("----------------------------------");
    }
}
