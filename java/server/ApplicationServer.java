package server;

import common.LoginInterface;
import common.PrintService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {
    public static void main(String[] args) throws RemoteException {
        System.setProperty("java.rmi.server.hostname", "localhost");

        System.setProperty("java.security.auth.login.config","/Users/lucacambiaghi/IdeaProjects/AuthPrintServer/src/main/resources/CustomLogin.conf");
        System.setProperty("java.security.policy","/Users/lucacambiaghi/IdeaProjects/AuthPrintServer/src/main/resources/Authorization.policy");

        PrintService service = new PrintServiceImplementation();
        LoginInterface loginObject = new LoginImpl(service);

        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("loginservice", loginObject);
        System.out.println("Registry created and rebinding completed");
    }
}
