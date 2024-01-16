package de.icp.match;

import de.icp.match.user.model.Department;
import de.icp.match.user.model.Trainer;
import de.icp.match.user.repository.DepartmentRepository;
import de.icp.match.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RootUserCreation implements ApplicationRunner {

    @Value("${digifrei.root.create:false}")
    private boolean shouldCreateRootUser;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    public RootUserCreation(UserRepository userRepository, PasswordEncoder passwordEncoder,
                            DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args)  {

        if(!this.shouldCreateRootUser) return;

        Department itDepartment = new Department("Informationstechnik");
        if (departmentRepository.existsByNameNot(itDepartment.getName())) departmentRepository.save(itDepartment);

        Trainer rootUser = new Trainer( "Root", "User", "root@example.com", passwordEncoder.encode("root"), itDepartment);

        if (!userRepository.existsByEmail(rootUser.getEmail())) {
            userRepository.save(rootUser);
            log.info("Successfully created root user");
        }
    }

}
