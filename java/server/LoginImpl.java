package server;

import common.LoginInterface;
import common.PrintService;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.IOException;

class LoginImpl extends java.rmi.server.UnicastRemoteObject implements LoginInterface {

    private PrintService myService;

    LoginImpl(PrintService service) throws java.rmi.RemoteException {
        myService = service;
    }

    public PrintService login(String username, String password) throws java.rmi.RemoteException, LoginException {

        LoginContext lc = new LoginContext("CustomJAASModule", new RemoteCallbackHandler(username, password));
        lc.login();
        Subject user = lc.getSubject();

        return new ServerProxy(user, myService);
    }
}

class RemoteCallbackHandler implements CallbackHandler {

    private String username;
    private String password;

    RemoteCallbackHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void handle(Callback[] callbackArray) throws UnsupportedCallbackException, IOException {
        for (Callback callback : callbackArray) {
            if (callback instanceof NameCallback) {
                NameCallback nc = (NameCallback) callback;
                nc.setName(username);
            } else if (callback instanceof PasswordCallback) {
                PasswordCallback pc = (PasswordCallback) callback;
                pc.setPassword(password.toCharArray());
            } else {
                throw new UnsupportedCallbackException
                        (callback, "Unrecognized Callback");
            }
        }
    }
}
