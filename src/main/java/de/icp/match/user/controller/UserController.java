package de.icp.match.user.controller;

import de.icp.match.api.UserApi;
import de.icp.match.dto.ChangeUserPasswordRequestDto;
import de.icp.match.dto.UserCreationDto;
import de.icp.match.dto.UserDto;
import de.icp.match.dto.UserUpdateDto;
import de.icp.match.user.mapper.UserMapper;
import de.icp.match.user.model.User;
import de.icp.match.user.service.UserCreationService;
import de.icp.match.user.service.UserQueryService;
import de.icp.match.user.service.UserUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@RestController
public class UserController implements UserApi {

    private final UserMapper userMapper;
    private final UserCreationService userCreationService;
    private final UserQueryService userQueryService;
    private final UserUpdateService userUpdateService;


    @Autowired
    public UserController(UserMapper userMapper, UserCreationService userCreationService, UserQueryService userQueryService, UserUpdateService userUpdateService) {
        this.userMapper = userMapper;
        this.userCreationService = userCreationService;
        this.userQueryService = userQueryService;
        this.userUpdateService = userUpdateService;
    }

    /**
     * POST /user/register : Creates and registers a new user in the application
     *
     * @param userCreationDto Object containing all information about the user who should be saved in the application (required)
     * @return The newly created user based on the information provided in the request body (status code 201)
     */
    @Override
    public ResponseEntity<UserDto> createUser(UserCreationDto userCreationDto) {

        User userToRegister = userMapper.toUser(userCreationDto);
        User registeredUser = userCreationService.register(userToRegister);

        UserDto registeredUserDto = userMapper.toDto(registeredUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserDto);
    }


    @Override
    public ResponseEntity<Void> checkIfUsernameIsTaken(String username) {
        return userQueryService.isUsernameTaken(username) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllUsersContainingSearchTerm(String searchTerm, Integer limit) {

        List<User> foundUsers = userQueryService.loadAllUsersContainingSearchTerm(searchTerm, limit);
        List<UserDto> userDTOs = userMapper.toDto(foundUsers);

        return ResponseEntity.ok(userDTOs);
    }

    /**
     * GET /user/{userId} : Returns a user by his user id
     *
     * @param userId The numeric id of the user for whom the desired action should be performed (required)
     * @return All information about the user whose id was specified in the path parameter (status code 200)
     */
    @Override
    public ResponseEntity<UserDto> getUserById(Integer userId) {

        try {
            User queriedUser =userQueryService.loadSingleUserById(userId);
            return ResponseEntity.ok(userMapper.toDto(queriedUser));
        }

        catch (EntityNotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    /**
     * PUT /user/{userId} : Updates an existing user by his user id
     *
     * @param userUpdateDto The user object containing all the new data that should be replaced. You have to pass the old value of a property if it should not be replaced  (required)
     * @return The updated user containing the new data provided in the request body (status code 200)
     */
    @Override
    public ResponseEntity<UserDto> updateUserById(Integer userId, UserUpdateDto userUpdateDto) {

        try {
            User currentlySavedUser = userQueryService.loadSingleUserById(userId);
            User updatedUser = userUpdateService.updateGeneralAccountInformation(userUpdateDto, currentlySavedUser);

            return ResponseEntity.ok(userMapper.toDto(updatedUser));
        }

        catch (EntityNotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Override
    public ResponseEntity<Void> changeUserPassword(Integer userId, ChangeUserPasswordRequestDto changeUserPasswordRequestDto) {

        try {
            String newPassword = changeUserPasswordRequestDto.getNewPassword();
            User user = userQueryService.loadSingleUserById(userId);
            userUpdateService.changePassword(user, newPassword);

            return ResponseEntity.noContent().build();
        }

        catch (EntityNotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    /**
     * DELETE /user/{userId} : Deletes an existing user by his user id
     *
     * @param userId The numeric id of the user for whom the desired action should be performed (required)
     * @return Specifies that deleting the user was performed successfully (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteUserById(Integer userId) {
        return null;
    }
}
