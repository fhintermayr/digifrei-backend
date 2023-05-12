package de.icp.match.user.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.icp.match.user.dto.apprentice.ApprenticeUpdateDto;
import de.icp.match.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link User} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ApprenticeUpdateDto.class)
})
public class UserUpdateDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private UUID departmentId;
}