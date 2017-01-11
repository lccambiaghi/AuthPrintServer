package login;

import java.security.Principal;

public class CustomPrincipal implements Principal, java.io.Serializable {
    protected String name;

    public CustomPrincipal(String name) {
        if (name == null)
            throw new NullPointerException("illegal null input");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return("CustomPrincipal:  " + name);
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof CustomPrincipal))
            return false;
        CustomPrincipal that = (CustomPrincipal) o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }

    public int hashCode() {
        return name.hashCode();
    }
}
