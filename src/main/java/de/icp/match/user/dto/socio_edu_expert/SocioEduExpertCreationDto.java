package de.icp.match.user.dto.socio_edu_expert;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link de.icp.match.user.model.SocioEduExpert}
 */
public record SocioEduExpertCreationDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email @NotBlank String email
) implements Serializable {
}