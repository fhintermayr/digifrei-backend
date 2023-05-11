package de.icp.match.user.dto;

import de.icp.match.user.model.Department;

public class TrainerDto extends UserDto {

    public TrainerDto(Integer id, String firstName, String lastName, String email, Department department) {
        super(id, firstName, lastName, email, department);
    }
}
