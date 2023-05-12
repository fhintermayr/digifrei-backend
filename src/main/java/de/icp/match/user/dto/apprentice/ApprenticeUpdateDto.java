package de.icp.match.user.dto.apprentice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.icp.match.user.dto.UserUpdateDto;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link de.icp.match.user.model.Apprentice} entity
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApprenticeUpdateDto extends UserUpdateDto implements Serializable {

    private final UUID socioEduExpertId;

    public ApprenticeUpdateDto(String firstName, String lastName, String email, UUID departmentId, UUID socioEduExpertId) {
        super(firstName, lastName, email, departmentId);
        this.socioEduExpertId = socioEduExpertId;
    }
}