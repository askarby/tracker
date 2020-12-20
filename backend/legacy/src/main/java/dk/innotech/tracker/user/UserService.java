package dk.innotech.tracker.user;

import dk.innotech.tracker.user.dto.UserDTO;
import dk.innotech.tracker.user.mapping.UserMapper;
import dk.innotech.tracker.user.persistence.UserEntity;
import dk.innotech.tracker.user.persistence.UserRepository;
import io.reactivex.Flowable;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Singleton;

@Singleton
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Flowable<UserDTO> fromValidCredentials(String username, String password) {
        return Flowable.fromPublisher(this.repository
                .findByUsername(username))
                .map((user) -> {
                    var isValid = BCrypt.checkpw(password, user.getEncodedPassword());
                    if (isValid) {
                        return user;
                    } else {
                        throw new InvalidUserCredentialsException(username);
                    }
                })
                .map((user) -> {
                    System.out.println(user);
                    return this.mapper.userEntityToUserDto(user);
                });
    }

    public Flowable<UserDTO> createUser(String username, String password, String fullName) {
        var salt = BCrypt.gensalt();
        var encodedPassword = BCrypt.hashpw(password, salt);
        var user = UserEntity.builder()
                .username(username)
                .encodedPassword(encodedPassword)
                .fullName(fullName)
                .build();
        return Flowable
                .fromPublisher(this.repository.save(user))
                .map(this.mapper::userEntityToUserDto);
    }
}
