package de.icp.match.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.icp.match.user.dto.apprentice.ApprenticeCreateDto;
import de.icp.match.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "userType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ApprenticeCreateDto.class, name = "APPRENTICE"),
        @JsonSubTypes.Type(value = TrainerCreationDto.class, name = "TRAINER")
})
public abstract class UserCreateDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long departmentId;
    private UserType userType;

    public UserCreateDto(String firstName, String lastName, String email, String password, Long departmentId, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.departmentId = departmentId;
        this.userType = userType;
    }

}