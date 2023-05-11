package de.icp.match.user.dto;

import de.icp.match.user.model.Department;
import de.icp.match.user.model.User;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserUpdateDto implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Department department;
}