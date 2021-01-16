package dk.innotech.user.security;

import dk.innotech.user.mappers.UserMapper;
import dk.innotech.user.repositories.UserRepository;
import dk.innotech.user.repositories.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            return userAndPassword;
        }
    }

    @Transactional(readOnly = true)
    UserDetailsFromDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(entity -> {
                    var roles = userRoleRepository.findAllByUserId(entity.getId());
                    var user = userMapper.toUserDto(entity, roles);
                    return new UserDetailsFromDTO(user, entity.getEncodedPassword());
                }).orElse(null);
    }
}