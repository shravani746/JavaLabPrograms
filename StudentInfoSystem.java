import java.util.*;

class Student {
    private int id;
    private String name;
    private int age;
    private List<String> courses = new ArrayList<>();

    public Student(int id, String name, int age, List<String> courses) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.courses = courses;
    }

    public int returnId() {
        return id;
    }

    public String returnName() {
        return name;
    }

    public int returnAge() {
        return age;
    }

    public List<String> returnCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Age: " + age + " | Courses: " + courses;
    }
}

class StudentManagementSystem {
    private Map<Integer, Student> students = new HashMap<>();

    public void addStudent(int id, String name, int age, List<String> courses) {
        if (students.containsKey(id)) {
            System.out.println("Student ID already exists!");
            return;
        }
        students.put(id, new Student(id, name, age, courses));
        System.out.println("Student added successfully.");
    }

    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : students.values()) {
            System.out.println(student);
        }
    }

    public void searchStudent(int id) {
        if (students.containsKey(id)) {
            System.out.println(students.get(id));
        } else {
            System.out.println("Student not found!");
        }
    }

    public void deleteStudent(int id) {
        if (students.remove(id) != null) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student ID not found!");
        }
    }

    public void listStudentsByCourse(String course) {
        boolean found = false;
        for (Student student : students.values()) {
            if (student.returnCourses().contains(course)) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No students found for the course: " + course);
        }
    }
}

public class StudentInfoSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        while (true) {
            System.out.println("\n1. Add Student\n2. View All Students\n3. Search Student by ID");
            System.out.println("4. Delete Student\n5. List Students by Course\n6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Courses (comma separated): ");
                    String[] coursesArray = scanner.nextLine().split(",");
                    List<String> courses = Arrays.asList(coursesArray);
                    sms.addStudent(id, name, age, courses);
                    break;
                case 2:
                    sms.viewAllStudents();
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    int searchId = scanner.nextInt();
                    sms.searchStudent(searchId);
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    int deleteId = scanner.nextInt();
                    sms.deleteStudent(deleteId);
                    break;
                case 5:
                    scanner.nextLine();
                    System.out.print("Enter Course Name: ");
                    String courseName = scanner.nextLine();
                    sms.listStudentsByCourse(courseName);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
