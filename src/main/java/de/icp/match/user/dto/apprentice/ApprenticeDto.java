package de.icp.match.user.dto.apprentice;


import de.icp.match.user.dto.UserDto;
import de.icp.match.user.model.Department;
import de.icp.match.user.model.SocioEduExpert;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link de.icp.match.user.model.Apprentice} entity
 */

@Getter
@Setter
public class ApprenticeDto extends UserDto {

    private final SocioEduExpert socioEduExpert;

    public ApprenticeDto(Integer id, String firstName, String lastName, String email, Department department, SocioEduExpert socioEduExpert) {
        super(id, firstName, lastName, email, department);
        this.socioEduExpert = socioEduExpert;
    }

}