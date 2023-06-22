package de.icp.match.user.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.icp.match.user.dto.apprentice.ApprenticeUpdateDto;
import de.icp.match.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "userType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ApprenticeUpdateDto.class, name = "APPRENTICE"),
        @JsonSubTypes.Type(value = TrainerUpdateDto.class, name = "TRAINER")
})
public class UserUpdateDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private Long departmentId;
    private UserType userType;

}