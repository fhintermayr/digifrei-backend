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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserQueryControllerTest {

    String sampleUsername = "john.doe";
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

    User secondSampleUser = User.builder()
            .firstName("Max")
            .lastName("Muster")
            .username("max.muster")
            .password("password123")
            .dateOfBirth(LocalDate.of(2000, 1, 3))
            .gender(Gender.DIVERSE)
            .profession("Professional Idiot")
            .department("IT")
            .roomNumber("1.1.1")
            .accessRole(AccessRole.ADMINISTRATOR)
            .build();

    User thirdSampleUser = User.builder()
            .firstName("Jane")
            .lastName("Doe")
            .username("jane.doe")
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

    @Test
    @DisplayName("querying all users with no search term provided returns a list of all users")
    public void queryingAllUsers_withNoSearchTermProvided_returnsAllUsers() throws Exception {

        insertUserIntoDatabase(sampleUser);
        insertUserIntoDatabase(secondSampleUser);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    @DisplayName("querying all users with a search term returns an empty list if no user matches the search term")
    public void queryingAllUsers_withSearchTermProvided_returnsEmptyList() throws Exception {

        insertUserIntoDatabase(sampleUser);
        insertUserIntoDatabase(secondSampleUser);

        mockMvc.perform(get("/user").param("searchTerm", "meier"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("querying all users with a search term returns a list of all users who match the search term")
    public void queryingAllUsers_withSearchTermProvided_returnsUsersMatchingSearchTerm() throws Exception {

        insertUserIntoDatabase(sampleUser);
        insertUserIntoDatabase(secondSampleUser);

        mockMvc.perform(get("/user").param("searchTerm", "doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("querying all users with search term and with limit returns amount of users specified in limit")
    public void queryAllUsersContainingSearchTermWithLimit() throws Exception {

        insertUserIntoDatabase(sampleUser);
        insertUserIntoDatabase(thirdSampleUser);

        mockMvc.perform(get("/user")
                        .param("searchTerm", "doe")
                        .param("limit", "1"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("querying a user with null as id returns 400 status code")
    public void queryUser_WithIdNull_returnsBadRequest() throws Exception {

        mockMvc.perform(get("/user/null"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("querying a user by his id returns the user object")
    public void queryUser_ById_returnsUser() throws Exception {

        insertUserIntoDatabase(sampleUser);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(sampleUser.firstName))
                .andExpect(jsonPath("$.username").value(sampleUser.username))
                .andExpect(jsonPath("$.id").value(sampleUser.id));

    }

    @Test
    @DisplayName("querying a user with non existing id returns 409 status code")
    public void queryUser_WithNonExistingId_returnsConflict() throws Exception {

        insertUserIntoDatabase(sampleUser);

        mockMvc.perform(get("/user/2"))
                .andExpect(status().isConflict());

    }


    private void insertUserIntoDatabase(User user) {
        userRepository.save(user);
    }

}