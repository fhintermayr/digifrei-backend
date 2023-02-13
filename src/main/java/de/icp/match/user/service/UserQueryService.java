package de.icp.match.user.service;

import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserQueryService {

    private final UserRepository userRepository;

    @Autowired
    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
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

}