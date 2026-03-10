import java.util.Scanner;

class Student3 {
    private String name;

    public Student3(String name) {
        this.name = name;
    }

    public String returnName() {
        return name;
    }

    public void displayInfo() {
        System.out.println("Name: "+ name);
    }
}

class Student extends Student3 {
    private int rollNumber;
    private String course;
    private double gpa;

    public Student(String name, int rollNumber, String course, double gpa) {
        super(name);
        this.rollNumber = rollNumber;
        this.course = course;
        this.gpa = gpa;
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

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Roll Number: "+rollNumber +  " Course: "+course+" GPA: "+gpa);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numStudents;
        while (true) {
            System.out.print("Enter the number of students: ");
            numStudents = scanner.nextInt();
            if (numStudents > 0) {
                break;
            } else {
                System.out.println("The number of students must be greater than 0.");
            }
        }

        Student[] students = new Student[numStudents];

        for (int i = 0; i < numStudents; i++) {
            System.out.println("\nEnter details for student " + (i + 1) + ":");

            scanner.nextLine(); 
            String name;
            while (true) {
                System.out.print("Name: ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    break;
                } else {
                    System.out.println("Name cannot be empty.");
                }
            }

             int rollNumber;
            while (true) {
                System.out.print("Roll Number: ");
                rollNumber = scanner.nextInt();
                boolean isUnique = true;
                if(rollNumber > 0)
                {
                for (int j = 0; j < i; j++) {
                    if (students[j].returnRollNumber() == rollNumber) {
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique) {
                    break;
                } else {
                    System.out.println("Roll Number must be unique.");
                }
                }else {
                	System.out.println("Roll Number must be greater than 0 and unique.");
                }
            }

            scanner.nextLine(); 
            String course;
            while (true) {
                System.out.print("Course: ");
                course = scanner.nextLine().trim();
                if (!course.isEmpty()) {
                    break;
                } else {
                    System.out.println("Course cannot be empty.");
                }
            }

            double gpa;
            while (true) {
                System.out.print("GPA (0 - 4): ");
                gpa = scanner.nextDouble();
                if (gpa >= 0.0 && gpa <= 4.0) {
                    break;
                } else {
                    System.out.println("GPA must be between 0 and 4.");
                }
            }

            students[i] = new Student(name, rollNumber, course, gpa);
        }

        System.out.println("\nDisplaying all Students...");
	System.out.println("=========================");
        for (Student student : students) {
            student.displayInfo();
            System.out.println("-----------------------------------");
        }

        Student topStudent = students[0];
        for (int i = 1; i < numStudents; i++) {
            if (students[i].returnGpa() > topStudent.returnGpa()) {
                topStudent = students[i];
            }
        }

        System.out.println("\nBest Performing Student:");
	System.out.println("==========================");
        topStudent.displayInfo();
	System.out.println("-----------------------------------");
        scanner.close();
    }
}
