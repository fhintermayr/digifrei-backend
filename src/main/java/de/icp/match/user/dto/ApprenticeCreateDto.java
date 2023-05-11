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
    private String doc;

    public ApprenticeCreateDto(String firstName, String lastName, String email, String password, String doc, UUID departmentId) {
        super(firstName, lastName, email, password, departmentId, UserType.APPRENTICE);
        this.doc = doc;
    }
}