package de.icp.match;

import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import de.icp.match.user.service.UserCreationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected JsonConverter jsonConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCreationService userCreationService;


    protected void insertUserIntoDatabase(User user) {
        userCreationService.register(user);
    }

    protected User loadUserFromDatabaseById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

}
