package dk.innotech.user.security;

import dk.innotech.user.dtos.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class UserDetailsFromDTO implements UserDetails, UserIdentifiable {
    private final UserDTO user;
    private final String password;

    public UserDetailsFromDTO(UserDTO user, String password) {
        this.user = user;
        this.password = password;
    }

    public UserDTO getDTO() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRolesWithExpiration().keySet().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        var expires = Instant.ofEpochMilli(user.getAccountExpiresOn());
        var now = Instant.now();
        return hasNonExpiredSystemRole() || expires.isAfter(now);
    }

    @Override
    public boolean isAccountNonLocked() {
        return hasNonExpiredSystemRole() || !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        var expires = Instant.ofEpochMilli(user.getCredentialsExpiresOn());
        var now = Instant.now();
        return hasNonExpiredSystemRole() || expires.isAfter(now);
    }

    @Override
    public boolean isEnabled() {
        // Account is always enabled
        return true;
    }

    private boolean hasNonExpiredSystemRole() {
        var systemRoleExpiration = user.getRolesWithExpiration().get(DefaultRole.SYSTEM.asText());
        return systemRoleExpiration != null && systemRoleExpiration > System.currentTimeMillis();
    }

    @Override
    public long getUserId() {
        return user.getId();
    }
}
