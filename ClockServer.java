import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ClockServer extends Remote {
    Date getCurrentTime() throws RemoteException;
}
