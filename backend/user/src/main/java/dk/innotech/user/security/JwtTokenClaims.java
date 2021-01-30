package dk.innotech.user.security;

public interface JwtTokenClaims {
    String ROLES_KEY = "roles";
    String USER_ID_KEY = "uid";
    String ACCOUNT_EXPIRES_KEY = "aexp";
    String ACCOUNT_LOCKED_KEY = "locked";
    String CREDENTIALS_EXPIRES_KEY = "cexp";
}
