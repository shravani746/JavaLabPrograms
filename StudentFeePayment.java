import java.util.Scanner;

class StudentFeePayment {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\n\n\t\t\tSTUDENT FEE PAYMENT\n\n");

        String name;
        while (true) {
            System.out.println("Enter Name: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
            } else {   
                break;
            }
        }

        String Class;
        while (true) {
            System.out.print("Enter Class: ");
            Class = scanner.nextLine();
            if (Class.isEmpty()) {
                System.out.println("Class cannot be empty.");
            } else {
                break;
            }
        }

        String mobile;
        while (true) {
            System.out.print("Enter Mobile Number: ");
            mobile = scanner.nextLine();
            if (mobile.length() == 10) {
                break;
            }
            System.out.println("Invalid Mobile Number. It must be exactly 10 digits.");
        }

        String email;
        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
            if (email.contains("@") && email.contains(".")) {
                break;
            }
            System.out.println("Invalid Email");
        }

        String accountNumber;
        while (true) {
            System.out.print("Enter Account Number: ");
            accountNumber = scanner.nextLine();
            if (accountNumber.length() > 10) {
                break;
            }
            System.out.println("Invalid Account Number. It must be more than 10 digits.");
        }

        double amt;
        while (true) {
            System.out.print("Enter Amount Paid: ");
            amt = scanner.nextDouble();
            if (amt > 0) {
                break;
            }
            System.out.println("Invalid Amount");
        }

        scanner.nextLine(); 

        StringBuffer details = new StringBuffer();
        details.append("Class: ").append(Class).append("\n");

        String maskedAccountNumber = "XXXXXX" + accountNumber.substring(accountNumber.length() - 4);
        details.append("Account Number: ").append(maskedAccountNumber).append("\n");

        details.append("Amount Paid: ").append(amt).append("\n");

        StringBuffer uppercase = new StringBuffer(name.toUpperCase());

        StringBuffer lowercase = new StringBuffer(email.toLowerCase());

        details.append("\nPayment Successful. Thank you for making the payment.\n");

        System.out.println("\n--- Student Fee Payment Receipt ---\n");
        System.out.println("Name: " + uppercase);
        System.out.println("Email: " + lowercase);
        System.out.println("Mobile Number: " + mobile);
        System.out.println();
        System.out.println(details);

        scanner.close();
    }
}
