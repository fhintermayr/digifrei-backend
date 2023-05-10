package de.icp.match;

import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class RootUserCreation implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RootUserCreation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args)  {
        User emp = new User( "bar", "mail", "mail", passwordEncoder.encode("pass"));

        if (!userRepository.existsByEmail(emp.getEmail())) userRepository.save(emp);
    }

}
