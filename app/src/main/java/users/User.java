package users;

import java.util.UUID;

public class User {
    final public String name;
    private final String email;
    private final UUID uuid;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.uuid = UUID.randomUUID();
    }

    public User(String name, String email, UUID uuid) {
        this.name = name;
        this.email = email;
        this.uuid = uuid;
    }
}
