package login;

public class UserPrincipal extends CustomPrincipal {
    public UserPrincipal(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return ("UserPrincipal:  " + this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof UserPrincipal))
            return false;
        UserPrincipal that = (UserPrincipal) o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }
}
