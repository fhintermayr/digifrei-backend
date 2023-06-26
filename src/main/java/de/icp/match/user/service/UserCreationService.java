package de.icp.match.user.service;

import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCreationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCreationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User userToRegister) {

       if (isEmailTaken(userToRegister.getEmail())) {
           throw new IllegalArgumentException("User with email " + userToRegister.getEmail() + " already exists");
       }

       userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
       // TODO: Add logging with AOP
       return userRepository.save(userToRegister);
    }

    private boolean isEmailTaken(String username) {
        return userRepository.existsByEmail(username);
    }
}
