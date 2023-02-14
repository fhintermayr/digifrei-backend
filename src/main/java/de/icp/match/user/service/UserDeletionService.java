package de.icp.match.user.service;

import de.icp.match.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

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
