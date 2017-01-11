package permissions;

public class CustomPermission extends java.security.BasicPermission {

    public CustomPermission(String name) {
        super(name);
    }

    public CustomPermission(String name, String actions) {
        super(name, actions);
    }
}
