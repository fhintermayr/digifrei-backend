package de.icp.match.user.service;

import de.icp.match.user.exception.DepartmentCreationException;
import de.icp.match.user.model.Department;
import de.icp.match.user.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(String departmentName) {

        Department departmentToCreate = new Department(departmentName);

        try {
            return departmentRepository.save(departmentToCreate);
        }
        catch (Exception e) {
            throw new DepartmentCreationException("Fehler beim Speichern der Abteilung in der Datenbank", e);
        }
    }

    public Department findById(Long departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow(EntityNotFoundException::new);
    }

    public Department changeName(Long departmentId, String newDepartmentName) {

        Department department = findById(departmentId);
        department.setName(newDepartmentName);

        return departmentRepository.save(department);
    }

    public List<Department> loadAllDepartments() {
        return departmentRepository.findAll();
    }
}
