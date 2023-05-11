package de.icp.match.user.dto;

import de.icp.match.user.model.Department;
import de.icp.match.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link User} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserDto {
    Integer id;
    String firstName;
    String lastName;
    String email;
    Department department;
}