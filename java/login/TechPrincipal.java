package login;

public class TechPrincipal extends CustomPrincipal {
    public TechPrincipal(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return ("TechPrincipal:  " + this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof TechPrincipal))
            return false;
        TechPrincipal that = (TechPrincipal) o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }
}
