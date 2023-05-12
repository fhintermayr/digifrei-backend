package de.icp.match.user.dto;

import de.icp.match.user.model.Apprentice;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link Apprentice} entity
 */
@Getter
@Setter
public class ApprenticeCreateDto extends UserCreateDto implements Serializable {

    private final UUID socioEduExpertId;

    public ApprenticeCreateDto(String firstName, String lastName, String email, String password, UUID departmentId, UUID socioEduExpertId) {
        super(firstName, lastName, email, password, departmentId, UserType.APPRENTICE);
        this.socioEduExpertId = socioEduExpertId;
    }
}