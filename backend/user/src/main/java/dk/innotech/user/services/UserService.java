package dk.innotech.user.services;

import dk.innotech.user.controllers.user.CreateUserRequest;
import dk.innotech.user.dtos.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    /*
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
     */

    public UserDTO createUser(CreateUserRequest request) {
        return null;
    }
}
