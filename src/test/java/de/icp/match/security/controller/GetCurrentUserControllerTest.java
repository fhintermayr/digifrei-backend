package de.icp.match.security.controller;

import de.icp.match.IntegrationTest;
import de.icp.match.dto.JwtResponseDto;
import de.icp.match.dto.LoginCredentialsDto;
import de.icp.match.user.model.AccessRole;
import de.icp.match.user.model.Gender;
import de.icp.match.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class GetCurrentUserControllerTest extends IntegrationTest {

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
    @DisplayName("attempting to get the current user with no jwt returns unauthorized")
    public void givenNoJwt_whenGetCurrentUser_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/auth/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("attempting to get the current user with a valid jwt returns the currently authenticated user")
    public void givenValidJwt_whenGetCurrentUser_thenReturnsAuthenticatedUser() throws Exception {

        insertUserIntoDatabase(sampleUser);

        String jwt = attemptLoginAndReturnJwt();

        mockMvc.perform(get("/auth/user")
                        .header("Authorization", "Bearer " + jwt))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(sampleUser.getUsername()));
    }

    private String attemptLoginAndReturnJwt() throws Exception {

        LoginCredentialsDto credentialsDto = new LoginCredentialsDto()
                .username("john.doe")
                .password("password123");

        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.toJson(credentialsDto)))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JwtResponseDto jwt = jsonConverter.fromJson(jsonResponse, JwtResponseDto.class);

        return jwt.getToken();
    }
}
