package login;

public class AdminPrincipal extends CustomPrincipal{

    public AdminPrincipal(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return ("AdminPrincipal:  " + this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof AdminPrincipal))
            return false;
        AdminPrincipal that = (AdminPrincipal) o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }
}
