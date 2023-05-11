package de.icp.match;

import de.icp.match.user.model.*;
import de.icp.match.user.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void run(ApplicationArguments args)  {
        Department dep = new Department("dep");
        Department dep2 = new Department("foo");
        SocioEduExpert see = new SocioEduExpert("hans", "wurst", "foo@bar.com");

        User emp = new Apprentice( "bar", "mail", "mail", passwordEncoder.encode("pass"), dep,see);
        User trainer = new Trainer( "bar", "mail", "mail2", passwordEncoder.encode("pass"), dep2);

        if (!userRepository.existsByEmail(emp.getEmail())) userRepository.save(emp);
        if (!userRepository.existsByEmail(trainer.getEmail())) userRepository.save(trainer);
    }

}
