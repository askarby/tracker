package dk.innotech.user.security;

import dk.innotech.user.dtos.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class UserDetailsFromDTO implements UserDetails {
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
        // TODO: 2021-01-14 - Needs to be inplemented with actual logic (dummy implementation for now)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO: 2021-01-14 - Needs to be inplemented with actual logic (dummy implementation for now)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO: 2021-01-14 - Needs to be inplemented with actual logic (dummy implementation for now)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO: 2021-01-14 - Needs to be inplemented with actual logic (dummy implementation for now)
        return true;
    }
}
