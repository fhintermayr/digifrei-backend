package de.icp.match.user.repository;

import de.icp.match.user.model.Employee;

import java.util.Optional;

public interface EmployeeRepository  {
    Optional<Employee> findByEmail(String email);
}
