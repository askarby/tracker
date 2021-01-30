package dk.innotech.user.security;

import dk.innotech.user.dtos.UserDTO;
import dk.innotech.user.entities.UserEntity;
import dk.innotech.user.mappers.UserMapper;
import dk.innotech.user.repositories.UserRepository;
import dk.innotech.user.repositories.UserRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test of CustomUserDetailsServiceTest")
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("should return UserDetailsFromDTO when user is found")
    public void userReturnsDetails() {
        var id = 42L;
        var username = "username";
        var userEntity = mock(UserEntity.class);
        var userDto = mock(UserDTO.class);

        when(userEntity.getId()).thenReturn(id);
        when(userRepository.findByUsername(username)).thenReturn(of(userEntity));
        when(userRoleRepository.findAllByUserId(id)).thenReturn(emptyList());
        when(userMapper.toUserDto(eq(userEntity), anyList())).thenReturn(userDto);

        var userDetails = service.loadUserByUsername(username);

        assertThat(userDetails).isNotNull();
    }

    @Test
    @DisplayName("should throw UsernameNotFoundException, if user is not found")
    public void throwUsernameNotFoundException() {
        var username = "username";
        when(userRepository.findByUsername(username)).thenReturn(empty());

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> service.loadUserByUsername(username));
    }
}