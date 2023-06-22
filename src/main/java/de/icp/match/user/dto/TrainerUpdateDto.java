package de.icp.match.user.dto;

public class TrainerUpdateDto extends UserUpdateDto {

    public TrainerUpdateDto(String firstName, String lastName, String email, Long departmentId) {
        super(firstName, lastName, email, departmentId, UserType.TRAINER);
    }
}
