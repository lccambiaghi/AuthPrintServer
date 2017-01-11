package common;

import javax.security.auth.login.LoginException;
import java.rmi.Remote;

public interface LoginInterface extends Remote {

     PrintService login(String username, String password)
            throws java.rmi.RemoteException, LoginException;
}
