package de.icp.match.user.service;

import de.icp.match.dto.UserUpdateDto;
import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import de.icp.match.user.mapper.UserUpdateMapper;
import org.springframework.stereotype.Service;

@Service
public class UserUpdateService {

    private final UserRepository userRepository;
    private final UserUpdateMapper userUpdateMapper;

    public UserUpdateService(UserRepository userRepository, UserUpdateMapper userUpdateMapper) {
        this.userRepository = userRepository;
        this.userUpdateMapper = userUpdateMapper;
    }

    public User updateGeneralAccountInformation(UserUpdateDto userWithUpdatedData, User userToUpdate) {
        User updatedUser = userUpdateMapper.partialUpdate(userWithUpdatedData, userToUpdate);

        return userRepository.save(updatedUser);
    }

}
