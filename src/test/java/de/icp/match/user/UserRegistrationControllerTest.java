package de.icp.match.user;

import de.icp.match.JsonConverter;
import de.icp.match.dto.AccessRoleDto;
import de.icp.match.dto.GenderDto;
import de.icp.match.dto.UserCreationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonConverter jsonConverter;
    private UserCreationDto userCreationDto;


    @Test
    @DisplayName("registering a user with all attributes provided returns the created user")
    public void registeringUser_withAllAttributesProvided_returnsCreatedUser() throws Exception {

        givenUserWithAllAttributes();

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.toJson(userCreationDto)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john.doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-01-03"))
                .andExpect(jsonPath("$.accessRole").value("MEMBER"));
    }

    @Test
    @DisplayName("registering a user with all optional attributes set to null returns the created user created user")
    public void registeringUser_withOptionalAttributesBeingNull_returnsCreatedUser() throws Exception {

        givenUserOptionalAttributesBeingNull();

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConverter.toJson(userCreationDto)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john.doe"))
                .andExpect(jsonPath("$.dateOfBirth").doesNotExist())
                .andExpect(jsonPath("$.roomNumber").doesNotExist());
    }

    @Test
    @DisplayName("registering a user with required attributes set to null returns bad request status code")
    public void registeringUser_withRequiredAttributesBeingNull_returnsBadRequestError() throws Exception {

        givenUserRequiredAttributesBeingNull();

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.toJson(userCreationDto)))

                .andExpect(status().isBadRequest());
    }

    private void givenUserWithAllAttributes() {
            this.userCreationDto = new UserCreationDto()
                    .firstName("John")
                    .lastName("Doe")
                    .username("john.doe")
                    .password("password123")
                    .dateOfBirth(LocalDate.of(2000, 1, 3))
                    .gender(GenderDto.DIVERSE)
                    .profession("Professional Idiot")
                    .department("IT")
                    .roomNumber("1.1.1")
                    .accessRole(AccessRoleDto.MEMBER);
    }

    private void givenUserOptionalAttributesBeingNull() {

        givenUserWithAllAttributes();

        this.userCreationDto
                .dateOfBirth(null)
                .roomNumber(null);
    }

    private void givenUserRequiredAttributesBeingNull() {

        givenUserWithAllAttributes();

        this.userCreationDto = new UserCreationDto();
    }

}