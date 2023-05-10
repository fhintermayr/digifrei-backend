package de.icp.match.user.service;

import de.icp.match.dto.UserUpdateDto;
import de.icp.match.user.mapper.UserUpdateMapper;
import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserUpdateService {

    private final UserRepository userRepository;
    private final UserUpdateMapper userUpdateMapper;
    private final PasswordEncoder passwordEncoder;


    public UserUpdateService(UserRepository userRepository, UserUpdateMapper userUpdateMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userUpdateMapper = userUpdateMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User updateGeneralAccountInformation(UserUpdateDto userWithUpdatedData, User userToUpdate) {
        User updatedUser = userUpdateMapper.partialUpdate(userWithUpdatedData, userToUpdate);

        return userRepository.save(updatedUser);
    }

    public void changePassword(User user, String newPasswordAsPlainText) {
        String encryptedPassword = passwordEncoder.encode(newPasswordAsPlainText);
        user.setPassword(encryptedPassword);

        userRepository.save(user);
    }
//
//    public User changeAccessRole(User user, AccessRole newAccessRole) {
//        user.setAccessRole(newAccessRole);
//
//        return userRepository.save(user);
//    }

}
