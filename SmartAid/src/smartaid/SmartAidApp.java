package smartaid;

import java.util.ArrayList;
import java.util.Scanner;

import smartaid.model.Volunteer;
import smartaid.exceptions.InvalidVolunteerException;

public class SmartAidApp {
    public static void main(String[] args) {
        ArrayList<Volunteer> volunteers = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== SMARTAID VOLUNTEER MANAGEMENT ===");

        while (true) {
            System.out.println("\n1. Add Volunteer");
            System.out.println("2. Display All Volunteers");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = Integer.parseInt(sc.nextLine());

                        if (age < 18)
                            throw new InvalidVolunteerException("Volunteer must be at least 18 years old.");

                        System.out.print("Enter Area Assigned: ");
                        String area = sc.nextLine();

                        volunteers.add(new Volunteer(name, age, area));
                        System.out.println("✅ Volunteer added successfully!");
                        break;

                    case 2:
                        if (volunteers.isEmpty()) {
                            System.out.println("No volunteers added yet!");
                        } else {
                            System.out.println("\n--- Volunteer List ---");
                            for (Volunteer v : volunteers)
                                v.display();
                        }
                        break;

                    case 3:
                        System.out.println("👋 Exiting SmartAid. Stay safe!");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid option. Try again!");
                }
            } catch (InvalidVolunteerException e) {
                System.out.println("❌ Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Unexpected error: " + e.getMessage());
            }
        }
    }
}
