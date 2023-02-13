package de.icp.match.user.controller;

import de.icp.match.IntegrationTest;
import de.icp.match.dto.ChangeUserPasswordRequestDto;
import de.icp.match.user.model.AccessRole;
import de.icp.match.user.model.Gender;
import de.icp.match.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChangePasswordControllerTest extends IntegrationTest {

    private User sampleUser;
    private ChangeUserPasswordRequestDto newPasswordDto;

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

        newPasswordDto = new ChangeUserPasswordRequestDto().newPassword("SuperSecret");
    }

    @Test
    @DisplayName("returns 409 if specified user does not exist")
    void changingUsersPassword_withNonExistentIdProvided_returnsConflict() throws Exception {

        insertUserIntoDatabase(sampleUser);

        mockMvc.perform(put("/user/4/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.toJson(newPasswordDto)))

                .andExpect(status().isConflict());

    }

    @Test
    @DisplayName("returns 204 if password was changed successfully")
    void changingUsersPassword_withValidRequest_returnsNoContent() throws Exception {

        insertUserIntoDatabase(sampleUser);

        mockMvc.perform(put("/user/" + sampleUser.getId() + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConverter.toJson(newPasswordDto)))

                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("results in user having a different password")
    void changingUsersPassword_resultsInUserHavingDifferentPassword() throws Exception {

        insertUserIntoDatabase(sampleUser);
        String usersOldPassword = loadUserFromDatabaseById(sampleUser.getId()).getPassword();

        mockMvc.perform(put("/user/" + sampleUser.getId() + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConverter.toJson(newPasswordDto)));

        String usersNewPassword = loadUserFromDatabaseById(sampleUser.getId()).getPassword();


        assertThat(usersOldPassword).isNotEqualTo(usersNewPassword);
    }
}