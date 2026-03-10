
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ReliefServerImpl extends UnicastRemoteObject implements ReliefServerInterface {

    ArrayList<ClientCallback> clients;

    protected ReliefServerImpl() throws RemoteException {
        clients = new ArrayList<>();
    }

    public void registerClient(ClientCallback client) throws RemoteException {

        clients.add(client);
        System.out.println("A volunteer registered for alerts.");

    }

    public void sendDisasterAlert(String message) throws RemoteException {

        System.out.println("Sending Alert: " + message);

        for(ClientCallback client : clients) {

            client.receiveAlert(message);

        }

    }

    public void reportDisaster(String report) throws RemoteException {

    System.out.println("Disaster Report from Volunteer: " + report);

    sendDisasterAlert(report);
}

}
