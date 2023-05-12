package de.icp.match.user.service;

import de.icp.match.user.model.Apprentice;
import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserQueryService {

    private final UserRepository userRepository;

    @Autowired
    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isEmailTaken(String username) {
        return userRepository.existsByEmail(username);
    }

    public List<User> loadAllUsersContainingSearchTerm(String searchTerm, Integer limit) {
        List<User> foundUsers = searchTerm == null ?
                userRepository.findAll() :
                userRepository.findUsersContaining(searchTerm);

        if (limit != null) return foundUsers.stream()
                .limit(limit)
                .collect(Collectors.toList());

        return foundUsers;
    }

    public User loadSingleUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    public Apprentice loadApprenticeById(Integer userId) {
        return (Apprentice) userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    // TODO: Maybe define boundary interface for other services to access this method
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    public boolean doesUserExist(User user) {
        return userRepository.existsById(user.getId());
    }

}
