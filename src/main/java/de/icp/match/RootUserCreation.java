package de.icp.match;

import de.icp.match.user.model.AccessRole;
import de.icp.match.user.model.Gender;
import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import de.icp.match.user.service.UserCreationService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("dev")
public class RootUserCreation implements ApplicationRunner {

    private final UserCreationService userCreationService;
    private final UserRepository userRepository;

    public RootUserCreation(UserCreationService userCreationService, UserRepository userRepository) {
        this.userCreationService = userCreationService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args)  {

            User rootUser = new User(
                    "admin",
                    "admin",
                    "admin",
                    "icp",
                    LocalDate.now(),
                    Gender.DIVERSE,
                    null,
                    "admin",
                    "admin",
                    "1.2.3",
                    null,
                    null,
                    null,
                    AccessRole.ADMINISTRATOR,
                    1
            );

        if (!userRepository.existsByUsername(rootUser.getUsername())) userCreationService.register(rootUser);
    }

}
