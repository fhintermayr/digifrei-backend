package de.icp.match.user.controller;

import de.icp.match.IntegrationTest;
import de.icp.match.user.model.AccessRole;
import de.icp.match.user.model.Gender;
import de.icp.match.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteUserControllerTest extends IntegrationTest {

    private User sampleUser;

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
    }

    @Test
    @DisplayName("deleting a user with an non existing id returns 409 status code")
    public void deletingUser_withNonExistentId_returnsConflictStatusCode() throws Exception {

        mockMvc.perform(delete("/user/" + 99))
                .andExpect(status().isConflict());

    }

    @Test
    @DisplayName("deleting an existing user returns 204 status code")
    public void deletingUser_returnsNoContentStatusCode() throws Exception {

        insertUserIntoDatabase(sampleUser);

        mockMvc.perform(delete("/user/" + sampleUser.getId()))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("deleted user does not exist in database anymore")
    public void deletingUser_resultsInUserNotBeingFoundInDatabase() throws Exception {

        insertUserIntoDatabase(sampleUser);

        mockMvc.perform(delete("/user/" + sampleUser.getId()));

        assertThrows(EntityNotFoundException.class, () -> loadUserFromDatabaseById(sampleUser.getId()));
    }


}
