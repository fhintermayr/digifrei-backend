package de.icp.match;

import de.icp.match.request.model.ExemptionCategory;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.model.ProcessingStatus;
import de.icp.match.request.model.RequestProcessing;
import de.icp.match.request.repository.ExemptionRequestRepository;
import de.icp.match.request.repository.RequestProcessingRepository;
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
    private final RequestProcessingRepository requestProcessingRepository;

    public RootUserCreation(UserRepository userRepository, PasswordEncoder passwordEncoder,
                            ExemptionRequestRepository exemptionRequestRepository, SocioEduExpertRepository socioEduExpertRepository,
                            RequestProcessingRepository requestProcessingRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.exemptionRequestRepository = exemptionRequestRepository;
        this.socioEduExpertRepository = socioEduExpertRepository;
        this.requestProcessingRepository = requestProcessingRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args)  {
        Department dep = new Department("dep");
        SocioEduExpert see = new SocioEduExpert("Frau", "Fachdienst", "fachidenst@icp.de");

        socioEduExpertRepository.save(see);

        Apprentice emp = new Apprentice( "Mr", "Madl", "mr@madl.de", passwordEncoder.encode("pass"), dep,see);
        Trainer trainer = new Trainer( "Herr", "Ausbilder", "mail2", passwordEncoder.encode("pass"), dep);

        if (!userRepository.existsByEmail(emp.getEmail())) userRepository.save(emp);
        if (!userRepository.existsByEmail(trainer.getEmail())) userRepository.save(trainer);


        ExemptionRequest exemptionRequest = new ExemptionRequest(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1),
                "irgendwas",
                ExemptionCategory.MEDICAL_APPOINTMENT,
                emp
        );


        ExemptionRequest exemptionRequest2 = new ExemptionRequest(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1),
                "irgendwas anderes",
                ExemptionCategory.OFFICE_APPOINTMENT,
                emp
        );

        exemptionRequestRepository.save(exemptionRequest);
        exemptionRequestRepository.save(exemptionRequest2);

        RequestProcessing rp = new RequestProcessing(exemptionRequest, ProcessingStatus.APPROVED, "komm ja nicht wieder", trainer);

        requestProcessingRepository.save(rp);

    }

}
