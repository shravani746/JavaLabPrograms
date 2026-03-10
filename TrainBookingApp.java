import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Booking class
class Booking {
    String name;
    int seatNo;

    Booking(String name, int seatNo) {
        this.name = name;
        this.seatNo = seatNo;
    }

    public String toString() {
        return seatNo + " - " + name;
    }
}

// Reservation System
class TrainReservationSystem {
    HashMap<Integer, String> seats = new HashMap<>();
    ArrayList<Booking> history = new ArrayList<>();

    TrainReservationSystem() {
        for (int i = 1; i <= 10; i++) {
            seats.put(i, null); // all seats empty
        }
    }

    public synchronized void bookSeat(String name, int age, int seatNo) throws InvalidBookingException {
        if (seats.get(seatNo) != null) {
            throw new InvalidBookingException("Seat " + seatNo + " is already booked!");
        }

        // Priority check
        if (age >= 70) {
            for (int i = 1; i <= 10; i++) {
                if (seats.get(i) == null) {
                    seatNo = i;
                    break;
                }
            }
            System.out.println(name + " (Senior Citizen) booked Seat " + seatNo);
        } else {
            System.out.println(name + " booked Seat " + seatNo);
        }

        seats.put(seatNo, name);
        history.add(new Booking(name, seatNo));
    }

    public void showHistory() {
        System.out.println("\nBooking History:");
        for (Booking b : history) {
            System.out.println(b);
        }
    }
}

// Runnable Task
class BookingTask implements Runnable {
    TrainReservationSystem system;
    String name;
    int age, seatNo;

    BookingTask(TrainReservationSystem system, String name, int age, int seatNo) {
        this.system = system;
        this.name = name;
        this.age = age;
        this.seatNo = seatNo;
    }

    public void run() {
        try {
            system.bookSeat(name, age, seatNo);
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

// Main class
public class TrainBookingApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TrainReservationSystem system = new TrainReservationSystem();

        while (true) {
            System.out.println("\n1. Book Seat\n2. Show History\n3. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    System.out.print("Enter Seat Number: ");
                    int seatNo = sc.nextInt();

                    Thread t = new Thread(new BookingTask(system, name, age, seatNo));
                    t.start();
                    try {
                        t.join();
                    } catch (Exception e) {}
                    break;

                case 2:
                    system.showHistory();
                    break;

                case 3:
                    System.out.println("Thank you for using the system.");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
