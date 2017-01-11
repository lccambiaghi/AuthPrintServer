package client;

import common.LoginInterface;
import common.PrintService;

import javax.security.auth.login.LoginException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {

        LoginInterface loginServer = (LoginInterface) Naming.lookup("rmi://localhost:5099/loginservice");

        try {
            PrintService service = loginServer.login("Cecilia", "Aragorn");
            service.print("file1", "1");
            service.setConfig("Param1", "Value");
            service.print("file2", "1");
        } catch (LoginException le) {
            //if login unsuccessful
            le.printStackTrace();
        }
    }
}
