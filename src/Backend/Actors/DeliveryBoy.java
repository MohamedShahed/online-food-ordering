package Backend.Actors;

import Backend.Actors.Person;

public class DeliveryBoy extends Person {
    public DeliveryBoy(String username, String email) {
        super(username, email);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }
}
