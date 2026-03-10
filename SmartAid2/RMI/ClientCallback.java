
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {

    void receiveAlert(String message) throws RemoteException;

}
