package de.icp.match.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQueryService {


    private final UserRepository userRepository;

    @Autowired
    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }
}
