package dk.innotech.user.security;

/**
 * These are default roles that must at all times exist in the system.
 */
public enum DefaultRole {
    SYSTEM("ROLE_SYSTEM"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String text;

    DefaultRole(String text) {
        this.text = text;
    }

    public String asText() {
        return text;
    }
}
