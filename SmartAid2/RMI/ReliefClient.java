import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ReliefClient extends UnicastRemoteObject implements ClientCallback {

    protected ReliefClient() throws Exception {
        super();
    }

    // Callback method - server calls this
    public void receiveAlert(String message) {
        System.out.println("\nALERT RECEIVED FROM SERVER:");
        System.out.println(message);
    }

    public static void main(String args[]) {

        try {

            Registry registry = LocateRegistry.getRegistry("localhost");

            ReliefServerInterface server =
                    (ReliefServerInterface) registry.lookup("ReliefService");

            ReliefClient client = new ReliefClient();

            // Register client for callback alerts
            server.registerClient(client);

            System.out.println("Volunteer registered for disaster alerts.");

            Scanner sc = new Scanner(System.in);

            while (true) {

                System.out.println("\nEnter disaster report to send to server:");
                String report = sc.nextLine();

                server.reportDisaster(report);
            }

        }

        catch (Exception e) {
            System.out.println(e);
        }

    }
}