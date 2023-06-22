package de.icp.match.user.dto.apprentice;

import de.icp.match.user.dto.UserType;
import de.icp.match.user.dto.UserUpdateDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link de.icp.match.user.model.Apprentice}
 */

@Getter
@Setter
public class ApprenticeUpdateDto extends UserUpdateDto implements Serializable {

    Long socioEduExpertId;

    public ApprenticeUpdateDto(String firstName, String lastName, String email, Long departmentId, Long socioEduExpertId) {
        super(firstName, lastName, email, departmentId, UserType.APPRENTICE);
        this.socioEduExpertId = socioEduExpertId;
    }
}