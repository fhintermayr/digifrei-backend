package de.icp.match;

import de.icp.match.request.model.ExemptionCategory;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.model.ProcessingStatus;
import de.icp.match.request.model.RequestProcessing;
import de.icp.match.request.repository.ExemptionRequestRepository;
import de.icp.match.request.repository.RequestProcessingRepository;
import de.icp.match.user.model.*;
import de.icp.match.user.repository.DepartmentRepository;
import de.icp.match.user.repository.SocioEduExpertRepository;
import de.icp.match.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("dev")
public class RootUserCreation implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExemptionRequestRepository exemptionRequestRepository;
    private final SocioEduExpertRepository socioEduExpertRepository;
    private final RequestProcessingRepository requestProcessingRepository;
    private final DepartmentRepository departmentRepository;

    public RootUserCreation(UserRepository userRepository, PasswordEncoder passwordEncoder,
                            ExemptionRequestRepository exemptionRequestRepository, SocioEduExpertRepository socioEduExpertRepository,
                            RequestProcessingRepository requestProcessingRepository,
                            DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.exemptionRequestRepository = exemptionRequestRepository;
        this.socioEduExpertRepository = socioEduExpertRepository;
        this.requestProcessingRepository = requestProcessingRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args)  {
        Department dep = new Department("Informationstechnik");
        Department dep2 = new Department("Hauswirtschaft");
        Department dep3 = new Department("Büro");
        Department dep4 = new Department("Metall");
        Department dep5 = new Department("Druck");
        Department dep6 = new Department("Orthopädietechnik");

        departmentRepository.saveAll(List.of(dep2,dep3,dep4,dep5,dep6));

        SocioEduExpert see = new SocioEduExpert("Frau", "Fachdienst", "fachisdenst@icp.de");
        SocioEduExpert see2 = new SocioEduExpert("Frau", "Fachdienst", "fachidenst@iscp.de");
        SocioEduExpert see3 = new SocioEduExpert("Frau", "Fachdienst", "fachaidenst@icp.de");
        SocioEduExpert see4 = new SocioEduExpert("Frau", "Fachdienst", "fachaidenst@icp.dsae");
        SocioEduExpert see5 = new SocioEduExpert("Frau", "Fachdienst", "fachaidasdenst@icp.de");
        SocioEduExpert see6 = new SocioEduExpert("Frau", "Fachdienst", "fachaidasdenst@sadicp.de");
        SocioEduExpert see7 = new SocioEduExpert("Frau", "Fachdienst", "fachasaidasdenst@icp.de");
        SocioEduExpert see8 = new SocioEduExpert("Horst", "Seehofer", "fachaidasdedasnst@icp.de");

        socioEduExpertRepository.save(see);
        socioEduExpertRepository.save(see2);
        socioEduExpertRepository.save(see3);
        socioEduExpertRepository.save(see4);
        socioEduExpertRepository.save(see5);
        socioEduExpertRepository.save(see6);
        socioEduExpertRepository.save(see7);
        socioEduExpertRepository.save(see8);

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

        List<ExemptionRequest> ex = new java.util.ArrayList<>(List.of());

        for (int i = 0; i < 49; i++) {
            var e = new ExemptionRequest(
                    LocalDateTime.now().plusDays(1),
                    LocalDateTime.now().plusDays(1),
                    "irgendwas anderes",
                    ExemptionCategory.OFFICE_APPOINTMENT,
                    emp
            );

            ex.add(e);
        }

        exemptionRequestRepository.saveAll(ex);
        exemptionRequestRepository.save(exemptionRequest);
        exemptionRequestRepository.save(exemptionRequest2);

        RequestProcessing rp = new RequestProcessing(exemptionRequest, ProcessingStatus.APPROVED, "komm ja nicht wieder", trainer);

        requestProcessingRepository.save(rp);

    }

}
