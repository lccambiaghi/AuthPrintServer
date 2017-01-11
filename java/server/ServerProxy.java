package server;

import common.PrintService;
import permissionvalidation.MethodCallAction;

import javax.security.auth.Subject;
import java.rmi.RemoteException;
import java.security.PrivilegedActionException;

class ServerProxy extends java.rmi.server.UnicastRemoteObject implements PrintService {

    private PrintService theService;
    private Subject theUser;

    ServerProxy(Subject user, PrintService theServer) throws java.rmi.RemoteException {
        this.theService = theServer;
        this.theUser = user;
    }

    public void print(String filename, String printer) throws RemoteException, InterruptedException, SecurityException {
        try{
            System.out.println("Method print invoked by " + theUser.getPrincipals());
            Subject.doAsPrivileged(theUser, new MethodCallAction("print"), null);
            theService.print(filename, printer);
        } catch (PrivilegedActionException ignored) {
            //if unsuccessful, no action is executed
        }
    }

    public String queue() throws RemoteException, SecurityException {
        try{
            System.out.println("Method queue invoked by " + theUser.getPrincipals());
            Subject.doAs(theUser, new MethodCallAction("queue"));
            theService.queue();
        } catch (PrivilegedActionException ignored) {

        }

        return null;
    }

    public void topQueue(int job) throws RemoteException, SecurityException {
        try{
            System.out.println("Method topQueue invoked by " + theUser.getPrincipals());
            Subject.doAs(theUser, new MethodCallAction("topQueue"));
            theService.topQueue(job);
        } catch (PrivilegedActionException ignored) {

        }
    }

    public void start() throws RemoteException, SecurityException {
        try{
            System.out.println("Method start invoked by " + theUser.getPrincipals());
            Subject.doAs(theUser, new MethodCallAction("start"));
            theService.start();
        } catch (PrivilegedActionException ignored) {

        }
    }

    public void stop() throws RemoteException, InterruptedException, SecurityException {
        try{
            System.out.println("Method stop invoked by " + theUser.getPrincipals());
            Subject.doAs(theUser, new MethodCallAction("stop"));
            theService.stop();
        } catch (PrivilegedActionException ignored) {

        }
    }

    public void restart() throws RemoteException, InterruptedException, SecurityException {
        try{
            System.out.println("Method restart invoked by " + theUser.getPrincipals());
            Subject.doAs(theUser, new MethodCallAction("restart"));
            theService.restart();
        } catch (PrivilegedActionException ignored) {

        }
    }

    public String status() throws RemoteException, SecurityException {
        try{
            System.out.println("Method status invoked by " + theUser.getPrincipals());
            Subject.doAs(theUser, new MethodCallAction("status"));
            theService.status();
        } catch (PrivilegedActionException ignored) {

        }
        return null;
    }

    public String readConfig(String parameter) throws RemoteException, SecurityException {
        try{
            System.out.println("Method readConfig invoked by " + theUser.getPrincipals());
            Subject.doAs(theUser, new MethodCallAction("readConfig"));
            theService.readConfig(parameter);
        } catch (PrivilegedActionException ignored) {

        }
        return null;
    }

    public void setConfig(String parameter, String value) throws RemoteException, SecurityException {
        try{
            System.out.println("Method setConfig invoked by " + theUser.getPrincipals());
            Subject.doAs(theUser, new MethodCallAction("setConfig"));
            theService.setConfig(parameter, value);
        } catch (PrivilegedActionException ignored) {

        }
    }

}

