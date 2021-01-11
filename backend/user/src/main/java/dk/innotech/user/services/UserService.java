package dk.innotech.user.services;

import dk.innotech.user.dtos.*;
import dk.innotech.user.entities.UserEntity;
import dk.innotech.user.entities.UserRoleEntity;
import dk.innotech.user.entities.UserRoleKey;
import dk.innotech.user.mappers.UserMapper;
import dk.innotech.user.repositories.RoleRepository;
import dk.innotech.user.repositories.UserRepository;
import dk.innotech.user.repositories.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(CreateUserDTO request) {
        // Determine if requested roles exist
        var requestedRoles = request.getRolesWithExpiration();
        var requestRoleNames = requestedRoles.keySet();
        var roles = roleRepository.findAllById(requestRoleNames);
        if (roles.size() < requestedRoles.size()) {
            throw new IllegalArgumentException("Unable to find roles for: " + String.join(", ", requestRoleNames));
        }

        // Create User (entity) - eg. persist user to database
        var encodedPassword = passwordEncoder.encode(request.getPassword());
        var user = UserEntity.builder()
                .username(request.getUsername())
                .encodedPassword(encodedPassword)
                .fullName(request.getFullName())
                .build();
        var persistedUser = userRepository.save(user);

        // Assign User (entity) with roles
        var userRoles = roles.stream().map(role -> {
            var key = UserRoleKey.builder()
                    .userId(persistedUser.getId())
                    .roleName(role.getName())
                    .build();
            var roleExpires = Instant.ofEpochMilli(requestedRoles.get(role.getName()));
            return UserRoleEntity.builder().id(key).role(role).user(persistedUser).expiresAt(roleExpires).build();
        }).collect(Collectors.toList());
        var persistedUserRoles = userRoleRepository.saveAll(userRoles);

        // Return DTO
        return userMapper.toUserDto(persistedUser, persistedUserRoles);
    }

    @Transactional
    public UserDTO registerUser(RegisterUserDTO request) {
        // TODO: 2021-01-10 - Needs to be implemented
        throw new IllegalStateException("NYI!");
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long id) {
        var user = userRepository.findById(id);
        var roles = userRoleRepository.findAllByUserId(id);
        return user.map(entity -> userMapper.toUserDto(entity, roles));
    }

    @Transactional(readOnly = true)
    public List<UserListingDTO> getUserListings() {
        return userRepository.findAll().stream()
                .filter(user -> user.getId() != 1L)
                .map(userMapper::toUserListingDto)
                .collect(toList());
    }
}
