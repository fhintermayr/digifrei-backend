package de.icp.match.user.dto;


import de.icp.match.user.model.Department;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link de.icp.match.user.model.Apprentice} entity
 */

@Getter
@Setter
public class ApprenticeDto extends UserDto {
    private final String doc;


    public ApprenticeDto(Integer id, String firstName, String lastName, String email, Department department, String doc) {
        super(id, firstName, lastName, email, department);
        this.doc = doc;
    }

}