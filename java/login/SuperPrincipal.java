package login;

public class SuperPrincipal extends CustomPrincipal {
    public SuperPrincipal(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return ("SuperPrincipal:  " + this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof SuperPrincipal))
            return false;
        SuperPrincipal that = (SuperPrincipal) o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }
}
