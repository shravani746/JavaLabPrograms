import java.util.Scanner;

class Student {
    private String name;
    private int id;
    private int[] marks;

    public Student(String name, int id, int[] marks) {
        this.name = name;
        this.id = id;
        this.marks = marks;
    }

    public synchronized void display() {
        System.out.println("\n=========================================");
        System.out.println("        Student Information");
        System.out.println("=========================================");
        System.out.println(" Name       : " + name);
        System.out.println(" Student ID : " + id);
        System.out.println("=========================================");
    }

    public synchronized void calculateGrade() {
        double total = 0;
        for (int mark : marks) {
            total += mark;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error in thread: " + e.getMessage());
            }
        }
        double average = total / marks.length;

        System.out.println(" Grades Processing...");
        System.out.println("-----------------------------------------");
        System.out.println(" Student ID  : " + id);
        System.out.println(" Name        : " + name);
        System.out.println(" Average Marks: " + (average));
        System.out.println("-----------------------------------------\n");
    }
}

class InfoThread extends Thread {
    private Student student;

    public InfoThread(Student student) {
        this.student = student;
    }

    public void run() {
        student.display();
    }
}

class GradeThread extends Thread {
    private Student student;

    public GradeThread(Student student) {
        this.student = student;
    }

    public void run() {
        student.calculateGrade();
    }
}

public class StudentInformationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=========================================");
        System.out.println("      STUDENT INFORMATION SYSTEM");
        System.out.println("=========================================\n");

        int num;
        while (true) {
            System.out.print("Enter number of students: ");
                num = scanner.nextInt();
                if (num > 0) break;
                else System.out.println("Error: Number of students must be greater than 0.");
            }
        scanner.nextLine();

        Student[] students = new Student[num];

        for (int i = 0; i < num; i++) {
            System.out.println("\n-----------------------------------------");
            System.out.println(" Enter details for Student " + (i + 1));
            System.out.println("-----------------------------------------");

            String name;
            while (true) {
                System.out.print(" Name       : ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) break;
                System.out.println("Error: Name cannot be empty.");
            }

            int id;
            while (true) {
                System.out.print(" Student ID : ");
                    id = scanner.nextInt();
                    if (id > 0) break;
                    else System.out.println("Error: Student ID must be greater than 0.");
                } 

            int numSubjects;
            while (true) {
                System.out.print(" Number of subjects: ");
                    numSubjects = scanner.nextInt();
                    if (numSubjects > 0) break;
                    else System.out.println("Error: Number of subjects must be greater than 0.");
                }

            int[] marks = new int[numSubjects];
            for (int j = 0; j < numSubjects; j++) {
                while (true) {
                    System.out.print("  Subject " + (j + 1) + " Marks (0-100): ");
                        int mark = scanner.nextInt();
                        if (mark >= 0 && mark <= 100) {
                            marks[j] = mark;
                            break;
                        } else {
                            System.out.println("Error: Marks must be between 0 and 100.");
                        }
                    } 
                    }   
            scanner.nextLine();  

            students[i] = new Student(name, id, marks);
        }

        System.out.println("\n=========================================");
        System.out.println("      PROCESSING STUDENT DATA...");
        System.out.println("=========================================\n");

        for (Student student : students) {
            Thread infoThread = new InfoThread(student);
            Thread gradeThread = new GradeThread(student);

            infoThread.start();
            gradeThread.start();

            try {
                infoThread.join();  
                gradeThread.join();
                Thread.sleep(500);  
            } catch (InterruptedException e) {
                System.out.println("Thread interruption error: " + e.getMessage());
            }
        }

        System.out.println("\n=========================================");
        System.out.println("   ALL STUDENT RECORDS PROCESSED! ");
        System.out.println("=========================================\n");

        scanner.close();
    }
}
