package de.icp.match.security.controller;

import de.icp.match.IntegrationTest;
import de.icp.match.dto.LoginCredentialsDto;
import de.icp.match.user.model.AccessRole;
import de.icp.match.user.model.Gender;
import de.icp.match.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends IntegrationTest {

    static User sampleUser = User.builder()
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

    @Test
    @DisplayName("attempting to login with invalid credentials returns unauthorized status code")
    public void attemptingLogin_withInvalidCredentials_returnsBadRequestError() throws Exception {

        LoginCredentialsDto credentialsDto = new LoginCredentialsDto()
                .username("foo")
                .password("bar");


        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConverter.toJson(credentialsDto)))

                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("attempting to login with valid credentials returns 200 status code")
    public void attemptingLogin_withValidCredentials_returnsOk() throws Exception {
        insertUserIntoDatabase(sampleUser);

        LoginCredentialsDto credentialsDto = new LoginCredentialsDto()
                .username("john.doe")
                .password("password123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConverter.toJson(credentialsDto)))

                .andExpect(status().isOk());
    }

}