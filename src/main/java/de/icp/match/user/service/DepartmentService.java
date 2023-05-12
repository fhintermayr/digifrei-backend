package de.icp.match.user.service;

import de.icp.match.user.model.Department;
import de.icp.match.user.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {


    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department findById(Long departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow(EntityNotFoundException::new);
    }
}
