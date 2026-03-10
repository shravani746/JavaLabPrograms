import java.util.Scanner;

public class Student2 {

    private int reg_no;
    private String name;
    private String[] sports;
    
    Student2(){}

    Student2(int reg_no, String name, String[] sports) {
        this.reg_no = reg_no;
        this.name = name;
        this.sports = sports;
    }

    public void display() {
        System.out.println("\nRegister Number: " + reg_no + "\nName: " + name);
    }

    public void display(boolean displaySports) {
        System.out.println("\nRegister Number: " + reg_no + "\nName: " + name + "\nSports: ");
        if (displaySports && sports.length > 0) {
            for (String sport : sports) {
                System.out.print(sport + ", ");
            }
            System.out.println("");  
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;
        while (true) {
            System.out.println("Enter the number of students: ");
            n = sc.nextInt();
            if (n > 0) {
                break;
            } else {
                System.out.println("The number of students cannot be 0.");
            }
        }

        Student2[] students = new Student2[n];

        for (int i = 0; i < n; i++) {
        	
            System.out.println("Enter the details of student "+(i+1)+": ");
            
            int reg_no;
            while (true) {
                System.out.println("Enter register number: ");
                reg_no = sc.nextInt();
                if (reg_no > 0) {
                    break; 
                } else {
                    System.out.println("Register number cannot be 0.");
                }
            }

            sc.nextLine();

            String name;
            while (true) {
                System.out.println("Enter name: ");
                name = sc.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty.");
                } else {
                    break; 
                }
            }

            int numSports;
            while (true) {
                System.out.println("Enter the number of sports " + name+ " participates in: ");
                numSports = sc.nextInt();
                if (numSports >= 0) {
                    break; 
                } else {
                    System.out.println("Number of sports cannot be negative.");
                }
            }
            sc.nextLine(); 
            
            String[] sports = new String[numSports];

            for (int j = 0; j < numSports; j++) {
                while (true) {
                    System.out.println("Enter sport " +(j+1)+": ");
                    String sport = sc.nextLine().trim();
                    if (sport.isEmpty()) {
                        System.out.println("Sport name cannot be empty.");
                    } else {
                        sports[j] = sport;
                        break; 
                    }
                }
            }

            students[i] = new Student2(reg_no, name, sports);
        }

        sc.close();

        System.out.println("\n\nDetails of Participants:");
        System.out.println("=========================");

        for (Student2 student : students) {
            if (student.sports.length > 0) {
                student.display(true); 
            }
        
            System.out.println("\n\nDetails of Non-Participant:");
            System.out.println("===========================");
            
        for (Student2 std : students) {
                if (std.sports.length == 0) {
                    std.display(); 
                }
        }
    }
}
}
