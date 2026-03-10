import studentinfo.Student;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\n\n\t\t\t\t\t\t\t\tSTUDENT INFORMATION SYSTEM");

        int numStudents;
        while (true) {
            System.out.print("Enter the number of students: ");
            numStudents = scanner.nextInt();
            scanner.nextLine();
            if (numStudents <= 0) {
                System.out.println("Value cannot be negative!");
            } else {
                break;
            }
        }

        Student[] students = new Student[numStudents];

        for (int i = 0; i < numStudents; i++) {
            System.out.println("\nEnter details for student " + (i + 1) + ":");

            String name;
            while (true) {
                System.out.print("Name of the Student: ");
                name = scanner.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Please enter the name.");
                } else {
                    break;
                }
            }

            int rollNumber;
            while (true) {
                System.out.print("Enter Student Roll Number: ");
                rollNumber = scanner.nextInt();
                scanner.nextLine();
                boolean unique = true;

                if (rollNumber > 0) {
                    for (int j = 0; j < i; j++) {
                        if (students[j].returnRollNumber() == rollNumber) {
                            unique = false;
                            break;
                        }
                    }
                    if (unique) {
                        break;
                    } else {
                        System.out.println("Roll Number must be unique.");
                    }
                } else {
                    System.out.println("Roll number must be greater than 0.");
                }
            }

            String course;
            while (true) {
                System.out.print("Enter Course Name: ");
                course = scanner.nextLine();
                if (course.isEmpty()) {
                    System.out.println("course name cannot be empty.");
                } else {
                    break;
                }
            }

            double gpa;
            while (true) {
                System.out.print("GPA (0- 4): ");
                gpa = scanner.nextDouble();
                scanner.nextLine();
                if (gpa < 0 || gpa > 4) {
                    System.out.println("Invalid GPA entered.");
                } else {
                    break;
                }
            }

            students[i] = new Student(name, rollNumber, course, gpa);
        }

        System.out.println("\n\nSuccesfully added Student Details :)");

        while (true) {

            System.out.println(
                    "\n\n1.Display all Students \n2.Search Student using name \n3.Students studying the same course \n4.Exit");
            System.out.println("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nStudent Information:");
                    for (Student student : students) {
                        student.displayInfo();
                    }
                    break;

                case 2:
                    System.out.println("Enter the name of the student you want to search :");
                    String searchName = scanner.nextLine();
                    for (Student search : students) {
                        if (searchName.equals(search.returnName())) {
                            System.out.println("Student Found : ");
                            System.out.println("Roll Number: " + search.returnRollNumber());
                            System.out.println("Course: " + search.returnCourse());
                            System.out.println("GPA: " + search.returnGpa());
                            System.out.println("-------------------------");
                            break;
                        } else {
                            System.out.println("Student Not Found.");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Enter the name of the course :");
                    String searchCourse = scanner.nextLine();
                    boolean found = false;
                    for (Student crs : students) {
                        if (searchCourse.equals(crs.returnCourse())) {
                            System.out.println("Student: " + crs.returnName());
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Course not found.");
                    }

                    break;

                case 4:
                    System.out.println("Exiting..");
                    scanner.close();
                    return;
            }
        }
    }
}
