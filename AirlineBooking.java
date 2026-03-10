import java.util.*;

class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

class Passengers {
    String name;
    int age;
    String type;

    public Passengers(String name, int age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }
}

class TicketBooking extends Thread {
    // Shared list across all threads
    public static List<String> BookedPassengers = new ArrayList<>();
    public Passengers p;

    public TicketBooking(Passengers p) {
        this.p = p;
    }

    public void run() {
        try {
            synchronized (BookedPassengers) { // Synchronizing on the shared list
                if (BookedPassengers.contains(p.name)) {
                    throw new BookingException("Ticket already booked by passenger: " + p.name);
                } else {
                    BookedPassengers.add(p.name);
                    System.out.println("Ticket booked for " + p.name + " (age: " + p.age + ") in " + p.type + " class.");
                }
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class AirlineBooking {
    public static void main(String[] args) {
        List<Passengers> pass = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of passengers:");
        int num = sc.nextInt();
        sc.nextLine();

        try {
            for (int i = 0; i < num; i++) {
                System.out.println("Enter the name of the passenger:");
                String name = sc.nextLine();

                System.out.println("Enter age:");
                int age = sc.nextInt();
                sc.nextLine();

                System.out.println("Enter class:");
                String type = sc.nextLine();

                pass.add(new Passengers(name, age, type));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        for (Passengers p : pass) {
            TicketBooking thread = new TicketBooking(p);
            thread.setPriority(p.type.equals("Business") ? Thread.MAX_PRIORITY : Thread.NORM_PRIORITY);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error : "+ e.getMessage());
            }
        }

        sc.close();
    }
}
