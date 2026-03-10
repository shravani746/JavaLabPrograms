
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReliefServerInterface extends Remote {

    void registerClient(ClientCallback client) throws RemoteException;

    void sendDisasterAlert(String message) throws RemoteException;

    void reportDisaster(String report) throws RemoteException;

}
