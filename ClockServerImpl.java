import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;

public class ClockServerImpl implements ClockServer {

    public ClockServerImpl() {}

    public Date getCurrentTime() throws RemoteException {
        Calendar calendar = Calendar.getInstance();
        // using sleep to increase response time
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    public static void main(String[] args) {
        try {
            ClockServerImpl clockServer = new ClockServerImpl();
            ClockServer stub = (ClockServer) UnicastRemoteObject.exportObject(clockServer, 0);

            Registry registry = LocateRegistry.createRegistry(8000);
            registry.bind("ClockServer", stub);

            System.out.println("Clock server ready.");
        } catch (Exception e) {
            System.out.println("Clock server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
