package de.icp.match.user.service;

import de.icp.match.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDeletionService {

    private final UserRepository userRepository;

    public UserDeletionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUserById(Integer userId) {

        boolean userDoesNotExist = !userRepository.existsById(userId);
        if (userDoesNotExist) throw new EntityNotFoundException();

        userRepository.deleteById(userId);
    }
}
