package de.icp.match.user.controller;

import de.icp.match.user.dto.DepartmentCreateDto;
import de.icp.match.user.model.Department;
import de.icp.match.user.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TRAINER')")
    public ResponseEntity<Department> createDepartment(@RequestBody @Valid DepartmentCreateDto departmentCreateDto) {

        Department createdDepartment = departmentService.createDepartment(departmentCreateDto.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {

        List<Department> allDepartments = departmentService.loadAllDepartments();

        return ResponseEntity.ok(allDepartments);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('TRAINER')")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentCreateDto departmentCreateDto) {

        String newDepartmentName = departmentCreateDto.name();
        Department updatedDepartment = departmentService.changeName(id, newDepartmentName);

        return ResponseEntity.ok(updatedDepartment);
    }
}
