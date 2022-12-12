package de.icp.match.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserCreationService implements UserCreationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserCreationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {

       user.username = getUniqueUsername(user.username);
       user.password = passwordEncoder.encode(user.password);

       // TODO:
        //  - Add logging with AOP
       return userRepository.save(user);
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
