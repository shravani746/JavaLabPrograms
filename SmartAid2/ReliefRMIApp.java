import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

// ================= REMOTE INTERFACE =================
interface ReliefService extends Remote {
    String registerUnit(String unitName) throws RemoteException;
    String getSystemStatus() throws RemoteException;
}

// ================= SERVER IMPLEMENTATION =================
class ReliefServiceImpl extends UnicastRemoteObject implements ReliefService {

    List<String> units;

    protected ReliefServiceImpl() throws RemoteException {
        units = new ArrayList<>();
    }

    public String registerUnit(String unitName) throws RemoteException {
        units.add(unitName);
        System.out.println("Unit Registered: " + unitName);
        return "Rescue Unit '" + unitName + "' registered successfully.";
    }

    public String getSystemStatus() throws RemoteException {
        return "Total Active Rescue Units: " + units.size();
    }
}

// ================= MAIN CLASS (SERVER + CLIENT) =================
public class ReliefRMIApp {

    public static void main(String[] args) {

        try {
            // Start RMI Registry
            LocateRegistry.createRegistry(1099);

            // Create Server Object
            ReliefService service = new ReliefServiceImpl();

            // Bind service to registry
            Naming.rebind("rmi://localhost/ReliefService", service);

            System.out.println("Disaster Relief RMI Server Started...");

            // ---------------- CLIENT PART ----------------
            // Lookup remote object
            ReliefService client =
                    (ReliefService) Naming.lookup("rmi://localhost/ReliefService");

            // Simulate rescue unit registration
            String unitName = "RescueUnit-" + (int)(Math.random() * 100);

            System.out.println("\nClient registering unit...");
            System.out.println(client.registerUnit(unitName));

            // Get system status
            System.out.println(client.getSystemStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
