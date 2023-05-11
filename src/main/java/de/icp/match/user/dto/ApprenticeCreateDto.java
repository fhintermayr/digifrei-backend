package de.icp.match.user.dto;

import de.icp.match.user.model.Apprentice;
import de.icp.match.user.model.Department;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Apprentice} entity
 */
@Getter
@Setter
public class ApprenticeCreateDto extends UserCreateDto implements Serializable {
    private String doc;

    public ApprenticeCreateDto(String firstName, String lastName, String email, String password, String doc, Department department) {
        super(firstName, lastName, email, password, department);
        this.doc = doc;
    }
}