package users;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private final String name;
    private final String email;
    private final UUID uuid;
    private final Map<UUID, Boolean> groups;

    public User(String name, String email, Map<UUID, Boolean> groups) {
        this.name = name;
        this.email = email;
        this.uuid = UUID.randomUUID();
        this.groups = groups;
    }

    public User(String name, String email, UUID uuid, Map<UUID, Boolean> groups) {
        this.name = name;
        this.email = email;
        this.uuid = uuid;
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map<UUID, Boolean> getGroups() {
        return groups;
    }
}
