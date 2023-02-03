package de.icp.match.user;

import de.icp.match.enums.AccessRole;
import de.icp.match.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCreationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private User userToRegister;
    @InjectMocks
    private UserCreationService userCreationService;

    @BeforeEach
    void setUp() {
        this.userToRegister = User.builder()
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("password123")
                .dateOfBirth(LocalDate.of(2000, 1, 3))
                .gender(Gender.DIVERSE)
                .profession("Professional Idiot")
                .department("IT")
                .roomNumber("1.1.1")
                .accessRole(AccessRole.MEMBER)
                .build();

        when(userRepository.save(userToRegister)).thenReturn(userToRegister);
        when(passwordEncoder.encode(anyString())).thenReturn("I'm a super secure password");
    }

    @Test
    public void registeringUser_withAvailableUsername_returnsUserWithSameUsernameAsProvided() {
        givenUserWithAvailableUsername();

        User registeredUser = userCreationService.register(userToRegister);

        assertThat(registeredUser.username).isEqualTo(userToRegister.username);
    }

    @Test
    public void registeringUser_withTakenUsernameNotEndingWithDigit_returnsUserWithUsernamePlusAppendedDigit() {
        givenUserWithTakenUsernameEndingWithNoDigit();

        User registeredUser = userCreationService.register(userToRegister);

        assertThat(registeredUser.username).isEqualTo("john.doe1");
    }

    @Test
    public void registeringUser_withTakenUsernameEndingWithSingleDigit_returnsUserWithUsernameEndingDigitBeingIncremented() {
        givenUserWithTakenUsernameEndingWithSingleDigit();

        User registeredUser = userCreationService.register(userToRegister);

        assertThat(registeredUser.username).isEqualTo("john.doe2");
    }

    @Test
    public void registeringUser_withTakenUsernameEndingWithDoubleDigit_returnsUserWithUsernameEndingDigitBeingIncremented() {
        givenUserWithTakenUsernameEndingWithDoubleDigit();

        User registeredUser = userCreationService.register(userToRegister);

        assertThat(registeredUser.username).isEqualTo("john.doe12");
    }

    @Test
    public void registeringUser_returnsUserWithEncryptedPassword() {
        givenUserWithAvailableUsername();
        String providedPassword = userToRegister.password;

        User registeredUser = userCreationService.register(userToRegister);

        assertThat(registeredUser.password).isNotEqualTo(providedPassword);
    }


    private void givenUserWithAvailableUsername() {
        when(userRepository.existsByUsername(this.userToRegister.username)).thenReturn(false);
    }

    private void givenUserWithTakenUsernameEndingWithNoDigit() {
        when(userRepository.existsByUsername(this.userToRegister.username)).thenReturn(true);
    }

    private void givenUserWithTakenUsernameEndingWithSingleDigit() {
        this.userToRegister.username = "john.doe1";

        when(userRepository.existsByUsername(this.userToRegister.username)).thenReturn(true);
    }

    private void givenUserWithTakenUsernameEndingWithDoubleDigit() {
        this.userToRegister.username ="john.doe11";

        when(userRepository.existsByUsername(this.userToRegister.username)).thenReturn(true);
    }
}