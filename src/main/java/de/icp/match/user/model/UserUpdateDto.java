package de.icp.match.user.model;

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
    private final String password;
    private final Department department;
}