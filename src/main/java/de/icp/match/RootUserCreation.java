package de.icp.match;

import de.icp.match.request.model.ExemptionCategory;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.repository.ExemptionRequestRepository;
import de.icp.match.user.model.*;
import de.icp.match.user.repository.SocioEduExpertRepository;
import de.icp.match.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("dev")
public class RootUserCreation implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExemptionRequestRepository exemptionRequestRepository;
    private final SocioEduExpertRepository socioEduExpertRepository;

    public RootUserCreation(UserRepository userRepository, PasswordEncoder passwordEncoder,
                            ExemptionRequestRepository exemptionRequestRepository, SocioEduExpertRepository socioEduExpertRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.exemptionRequestRepository = exemptionRequestRepository;
        this.socioEduExpertRepository = socioEduExpertRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args)  {
        Department dep = new Department("dep");
        Department dep2 = new Department("foo");
        SocioEduExpert see = new SocioEduExpert("hans", "wurst", "foo@bar.com");

        socioEduExpertRepository.save(see);

        Apprentice emp = new Apprentice( "bar", "mail", "mail", passwordEncoder.encode("pass"), dep,see);
        Trainer trainer = new Trainer( "bar", "mail", "mail2", passwordEncoder.encode("pass"), dep2);

        if (!userRepository.existsByEmail(emp.getEmail())) userRepository.save(emp);
        if (!userRepository.existsByEmail(trainer.getEmail())) userRepository.save(trainer);


        ExemptionRequest exemptionRequest = new ExemptionRequest(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1),
                "irgendwas",
                ExemptionCategory.MEDICAL_APPOINTMENT,
                emp
        );

        exemptionRequestRepository.save(exemptionRequest);
    }

}
