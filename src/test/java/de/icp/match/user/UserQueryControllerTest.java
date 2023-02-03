package de.icp.match.user;

import de.icp.match.enums.AccessRole;
import de.icp.match.enums.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserQueryControllerTest {

    String sampleUsername = "max.muster";
    User sampleUser = User.builder()
            .firstName("John")
            .lastName("Doe")
            .username(sampleUsername)
            .password("password123")
            .dateOfBirth(LocalDate.of(2000, 1, 3))
            .gender(Gender.DIVERSE)
            .profession("Professional Idiot")
            .department("IT")
            .roomNumber("1.1.1")
            .accessRole(AccessRole.MEMBER)
            .build();


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("checking if username is taken returns 200 status code if it is taken")
    public void checkingUsernameAvailability_withTakenUsername_returnsOkStatusCode() throws Exception {

        insertUserIntoDatabase(sampleUser);

        mockMvc.perform(head("/user/" + sampleUsername))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("checking if username is taken returns 409 status code if it is taken")
    public void checkingUsernameAvailability_withAvailableUsername_returnsConflictStatusCode() throws Exception {

        mockMvc.perform(head("/user/" + sampleUsername))
                .andExpect(status().isConflict());

    }


    private void insertUserIntoDatabase(User user) {
        userRepository.save(user);
    }

}