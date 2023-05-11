package de.icp.match.user.dto;

import de.icp.match.user.model.Trainer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link Trainer} entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TrainerCreationDto extends UserCreateDto implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final UUID departmentId;

    public TrainerCreationDto(String firstName, String lastName, String email, String password, UUID departmentId) {
        super(firstName, lastName, email, password, departmentId, UserType.TRAINER);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.departmentId = departmentId;
    }
}