package de.icp.match.user;

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

       userToRegister.username = getUniqueUsername(userToRegister.username);
       userToRegister.password = passwordEncoder.encode(userToRegister.password);

       // TODO: Add logging with AOP
       return userRepository.save(userToRegister);
    }

    private String getUniqueUsername(String username) {

        String uniqueUsername = username;
        int numberSuffix = endsWithNumber(username) ? getNumberSuffix(username) : 0;

        // TODO: Refactor
        while (isUsernameTaken(uniqueUsername)) {

            numberSuffix++;
            uniqueUsername = username.replaceAll("\\d+","") + numberSuffix;
        }

        return uniqueUsername;
    }

    private int getNumberSuffix(String username) {

        String numberSuffixAsString = username.replaceAll("\\D+","");

        return Integer.parseInt(numberSuffixAsString);
    }

    private boolean endsWithNumber(String input) {
        char lastCharOfString = getLastCharOfString(input);

        return Character.isDigit(lastCharOfString);
    }

    private char getLastCharOfString(String input) {
        return input.charAt(input.length()-1);
    }

    private boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }
}
