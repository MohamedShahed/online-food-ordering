package Backend.Actors;

public abstract class Person {
    protected String name;
    protected String email;
    protected String password;

    public Person(String username, String email) {
        this.name=username;
        this.email=email;
    }

    public abstract String getName();
    public abstract String getEmail();
}
