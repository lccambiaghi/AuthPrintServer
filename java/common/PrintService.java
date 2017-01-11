package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintService extends Remote {

    void print(String filename, String printer) throws RemoteException, InterruptedException, SecurityException;

    String queue() throws RemoteException, SecurityException;

    void topQueue(int job) throws RemoteException, SecurityException;

    void start() throws RemoteException, SecurityException;

    void stop() throws RemoteException, InterruptedException, SecurityException;

    void restart() throws RemoteException, InterruptedException, SecurityException;

    String status() throws RemoteException, SecurityException;

    String readConfig(String parameter) throws RemoteException, SecurityException;

    void setConfig(String parameter, String value) throws RemoteException, SecurityException;

}
