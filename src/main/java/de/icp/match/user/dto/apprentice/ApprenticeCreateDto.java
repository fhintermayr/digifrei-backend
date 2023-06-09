package de.icp.match.user.dto.apprentice;

import de.icp.match.user.dto.UserCreateDto;
import de.icp.match.user.dto.UserType;
import de.icp.match.user.model.Apprentice;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Apprentice} entity
 */
@Getter
@Setter
public class ApprenticeCreateDto extends UserCreateDto implements Serializable {

    private final Long socioEduExpertId;

    public ApprenticeCreateDto(String firstName, String lastName, String email, String password, Long departmentId, Long socioEduExpertId) {
        super(firstName, lastName, email, password, departmentId, UserType.APPRENTICE);
        this.socioEduExpertId = socioEduExpertId;
    }
}