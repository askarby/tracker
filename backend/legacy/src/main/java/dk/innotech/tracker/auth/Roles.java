package dk.innotech.tracker.auth;

interface RoleStatics {
    String PREFIX = "ROLE_";
}

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN");

    private String name;

    Roles(String name) {
        if (!name.startsWith(RoleStatics.PREFIX)) {
            name = RoleStatics.PREFIX + name;
        }
        this.name = name;
    }
}
