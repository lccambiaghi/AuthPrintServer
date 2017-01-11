package login;

import hashing.BCrypt;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CustomLoginModule implements LoginModule {

    private static final int NOT = 0, OK = 1, COMMIT = 2;

    private Subject subject;
    private CustomPrincipal entity;
    private CallbackHandler callbackhandler;
    private int status;

    public void initialize(Subject subject, CallbackHandler callbackhandler, Map state, Map options) {
        status = NOT;
        entity = null;
        this.subject = subject;
        this.callbackhandler = callbackhandler;
    }

    public boolean login() throws LoginException {

        Callback callbacks[] = new Callback[2];
        callbacks[0] = new NameCallback("Username: ");
        callbacks[1] = new PasswordCallback("Password: ", false);

        try {
            callbackhandler.handle(callbacks);
        } catch (java.io.IOException ioe) {
            throw new LoginException(ioe.toString());
        } catch (UnsupportedCallbackException ce) {
            throw new LoginException("Error: " + ce.getCallback().toString());
        }

        NameCallback nameCallback = (NameCallback) callbacks[0];
        PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];

        String inUsername = nameCallback.getName();
        String inPassword = new String(passwordCallback.getPassword());

        Scanner sc = null;
        try {
            sc = new Scanner(new File("/Users/lucacambiaghi/IdeaProjects/AuthPrintServer/src/main/resources/credentials.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String fileUsername= line.split(";")[0];
            String role = line.split(";")[1];
            if(fileUsername.equals(inUsername)){
                String storedSalt = line.split(";")[1];
                String storedHashed = line.split(";")[2];
                String inHashed = BCrypt.hashpw(inPassword, storedSalt);
                if(storedHashed.equals(inHashed)){

                    switch (role){
                        case "admin":
                            entity = new AdminPrincipal(inUsername);
                            break;
                        case "technician":
                            entity = new TechPrincipal(inUsername);
                            break;
                        case "super":
                            entity = new SuperPrincipal(inUsername);
                            break;
                        default:
                            entity = new UserPrincipal(inUsername);
                    }

                    status = OK;
                    return true;
                }
                else{
                    throw new FailedLoginException("Wrong password");
                }
            }
        }
        throw new FailedLoginException("Username not found");
    }

    public boolean commit() throws LoginException {
        if (status == NOT || subject == null) {
            return false;
        } else {
            Set entities = subject.getPrincipals();
            if (!entities.contains(entity)) {
                entities.add(entity);
            }
            status = COMMIT;
            return true;
        }
    }

    public boolean abort() throws LoginException {
        if ((subject != null) && (entity != null)) {
            Set entities = subject.getPrincipals();
            if (entities.contains(entity)) {
                entities.remove(entity);
            }
        }
        subject = null;
        entity = null;
        status = NOT;
        return true;
    }

    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(entity);
        status = NOT;
        subject = null;
        return true;
    }
}
