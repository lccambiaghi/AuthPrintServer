package permissionvalidation;

import permissions.CustomPermission;

import java.security.*;

public class MethodCallAction implements PrivilegedExceptionAction {

    private String priveledgedMethodName;

    public MethodCallAction(String methodName) {
        priveledgedMethodName = methodName;
    }

    public Object run() throws PrivilegedActionException {

        try{
            AccessController.checkPermission(new CustomPermission(priveledgedMethodName));
        } catch (AccessControlException e) {
            System.out.println("Operation denied. Permission needed: " + e.getPermission());
            throw new PrivilegedActionException(e);
        }

        //if successful
        return null;
    }
}
