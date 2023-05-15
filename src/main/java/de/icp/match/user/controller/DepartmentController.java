package de.icp.match.user.controller;

import de.icp.match.user.model.Department;
import de.icp.match.user.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("department")
    public ResponseEntity<List<Department>> getAllDepartments() {

        List<Department> allDepartments = departmentService.loadAllDepartments();

        return ResponseEntity.ok(allDepartments);
    }
}
