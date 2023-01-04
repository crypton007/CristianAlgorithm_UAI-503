import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClockSyncClient {

    public static void calculateSynchronizedTime(long requestTime, long responseTime,Date serverTime){
        // Get the actual time on the client's clock
        Calendar calendar = Calendar.getInstance();
        Date actualTime = calendar.getTime();

        // Calculate the process delay latency i.e. (T1-T0)
        long processDelayLatency = responseTime - requestTime;

        // Synchronize the client's clock with the server's clock
        // Tclient = Tserver + (T1-T0)/2
        Calendar milliCalendar = Calendar.getInstance();
        milliCalendar.setTime(serverTime);
        milliCalendar.add(milliCalendar.MILLISECOND, (int)processDelayLatency);
        Date synchronizedTime = milliCalendar.getTime();

        // Calculate the synchronization error
        long error = actualTime.getTime() - synchronizedTime.getTime();

        //Set Date Format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        // Print the results
        System.out.println("Request Time: " + dateFormat.format(requestTime));
        System.out.println("Response Time: " + dateFormat.format(responseTime));
        System.out.println("Process Delay Latency: " + processDelayLatency + " milliseconds");
        System.out.println("Time returned by server: " + dateFormat.format(serverTime));
        System.out.println("Actual clock time at client side: " + dateFormat.format(actualTime));
        System.out.println("Synchronized process client time: " + dateFormat.format(synchronizedTime));
        System.out.println("Synchronization error: " + error + " milliseconds");
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        // //Set Date Format
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        // Connect to the clock server
        Registry registry = LocateRegistry.getRegistry("localhost", 8000);
        ClockServer clockServer = (ClockServer) registry.lookup("ClockServer");

        // Get the current time T0
        long requestTime = System.currentTimeMillis();

        // Request the current time from the server
        Date serverTime = clockServer.getCurrentTime();
        
        // Get the response Time T1
        long responseTime = System.currentTimeMillis();

        calculateSynchronizedTime(requestTime,responseTime,serverTime);


    }
    
}

