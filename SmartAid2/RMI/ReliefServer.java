import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ReliefServer {

    public static void main(String args[]) {

        try {

            ReliefServerImpl server = new ReliefServerImpl();

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("ReliefService", server);

            System.out.println("Disaster Relief Server is running...");

            Scanner sc = new Scanner(System.in);

            while(true)
            {
                System.out.println("\nEnter Disaster Alert Message:");

                String message = sc.nextLine();

                server.sendDisasterAlert(message);
            }

        }

        catch(Exception e)
        {
            System.out.println(e);
        }

    }

}