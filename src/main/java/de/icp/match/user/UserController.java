package de.icp.match.user;

import de.icp.match.api.UserApi;
import de.icp.match.dto.UserCreationDto;
import de.icp.match.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController implements UserApi {

    private final UserMapper userMapper;
    private final UserCreationService userCreationService;

    @Autowired
    public UserController(UserMapper userMapper, UserCreationService userCreationService) {
        this.userMapper = userMapper;
        this.userCreationService = userCreationService;

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

        UserDto registeredUserDto = userMapper.toUserDto(registeredUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserDto);
    }

    /**
     * GET /user/all : Returns all registered users
     *
     * @param maxResults The maximum number of results to be returned per page. This can be any number greater than 0.  (optional, default to 50)
     * @return A list containing all registered application users (status code 200)
     */
    @Override
    public ResponseEntity<List<UserDto>> getAllUsers(Long maxResults) {
        return null;
    }

    /**
     * GET /user/{userId} : Returns a user by his user id
     *
     * @param userId The numeric id of the user for whom the desired action should be performed (required)
     * @return All information about the user whose id was specified in the path parameter (status code 200)
     */
    @Override
    public ResponseEntity<UserDto> getUserById(Integer userId) {
        return null;
    }

    /**
     * PUT /user/{userId} : Updates an existing user by his user id
     *
     * @param userId  The numeric id of the user for whom the desired action should be performed (required)
     * @param userDto The user object containing all the new data that should be replaced. You have to pass the old value of a property if it should not be replaced  (required)
     * @return The updated user containing the new data provided in the request body (status code 200)
     */
    @Override
    public ResponseEntity<UserDto> updateUserById(Integer userId, UserDto userDto) {
        return null;
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
