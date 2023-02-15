package de.icp.match.user.controller;

import de.icp.match.IntegrationTest;
import de.icp.match.dto.AccessRoleDto;
import de.icp.match.dto.ChangeUsersAccessRoleRequestDto;
import de.icp.match.dto.GenderDto;
import de.icp.match.dto.UserDto;
import de.icp.match.user.model.AccessRole;
import de.icp.match.user.model.Gender;
import de.icp.match.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateUserControllerTest extends IntegrationTest {

    private final String UPDATE_USER_PATH = "/user/";
    private User sampleUser;
    private UserDto sampleUserDto;
    private ChangeUsersAccessRoleRequestDto administratorAccessRoleDto;

    @BeforeEach
    void setUp() {
        sampleUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("password123")
                .dateOfBirth(LocalDate.of(2000, 1, 3))
                .gender(Gender.DIVERSE)
                .profession("Professional Idiot")
                .department("IT")
                .roomNumber("1.1.1")
                .accessRole(AccessRole.MEMBER)
                .build();

        sampleUserDto = new UserDto()
                .firstName(sampleUser.getFirstName())
                .lastName(sampleUser.getLastName())
                .username(sampleUser.getUsername())
                .dateOfBirth(sampleUser.getDateOfBirth())
                .gender(GenderDto.valueOf(sampleUser.getGender().name()))
                .profession(sampleUser.getProfession())
                .department(sampleUser.getDepartment())
                .roomNumber(sampleUser.getRoomNumber())
                .accessRole(AccessRoleDto.valueOf(sampleUser.getAccessRole().name()))
                .id(1);

        administratorAccessRoleDto = new ChangeUsersAccessRoleRequestDto().newAccessRole(AccessRoleDto.ADMINISTRATOR);
    }


    @Test
    @DisplayName("returns 400 status code if no user is provided in the request body")
    public void providingNoUserInRequestBody_returnsBadRequest() throws Exception {

        mockMvc.perform(put(UPDATE_USER_PATH + "1")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns the updated user when a valid user object is provided in the request body")
    public void sendingValidUser_returnsUpdatedUserObject() throws Exception {

        insertUserIntoDatabase(sampleUser);

        mockMvc.perform(put(UPDATE_USER_PATH + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.toJson(sampleUserDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleUser.getId()))
                .andExpect(jsonPath("$.username").value("john.doe"))
                .andExpect(jsonPath("$.preferences").doesNotExist());
    }

    @Test
    @DisplayName("returns 409 status code if there is no user with the provided id")
    public void sendingValidUser_withNonExistingId_returnsConflictStatusCode() throws Exception {

        insertUserIntoDatabase(sampleUser);
        sampleUserDto.setId(2);

        mockMvc.perform(put(UPDATE_USER_PATH + sampleUserDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.toJson(sampleUserDto)))

                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("updating users access role returns the user with the new access role")
    void changingUsersAccessRole_returnsUserWithUpdatedAccessRole() throws Exception {

        insertUserIntoDatabase(sampleUser);

        mockMvc.perform(put("/user/" + sampleUser.getId() + "/access-role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.toJson(administratorAccessRoleDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessRole").value("ADMINISTRATOR"));
    }
}
