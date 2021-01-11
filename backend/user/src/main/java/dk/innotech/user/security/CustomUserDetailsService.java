package dk.innotech.user.security;

import dk.innotech.user.dtos.UserDTO;
import dk.innotech.user.mappers.UserMapper;
import dk.innotech.user.repositories.UserRepository;
import dk.innotech.user.repositories.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userAndPassword = getUserByUsername(username);
        if (userAndPassword == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        } else {
            var user = userAndPassword.getFirst();
            var password = userAndPassword.getSecond();
            var roles = user.getRolesWithExpiration().keySet().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(toList());
            return new User(user.getUsername(), password, roles);
        }
    }

    @Transactional(readOnly = true)
    Pair<UserDTO, String> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(entity -> {
                    var roles = userRoleRepository.findAllByUserId(entity.getId());
                    var user = userMapper.toUserDto(entity, roles);
                    return Pair.of(user, entity.getEncodedPassword());
                }).orElse(null);
    }
}