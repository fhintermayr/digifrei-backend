package de.icp.match.user.mapper;

import de.icp.match.user.model.Apprentice;
import de.icp.match.user.dto.UserUpdateDto;
import de.icp.match.user.model.Trainer;
import de.icp.match.user.model.User;
import de.icp.match.user.service.DepartmentService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {DepartmentService.class})
public interface UserUpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "departmentId", target = "department")
    void partialUpdate(UserUpdateDto userWithUpdatedData, @MappingTarget Apprentice apprenticeToUpdate);

    @Mapping(source = "departmentId", target = "department")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UserUpdateDto userWithUpdatedData, @MappingTarget Trainer trainerToUpdate);

    default void partialUpdateUser(UserUpdateDto userWithUpdatedData, User userToUpdate) {
        if (userToUpdate instanceof Apprentice) {
            partialUpdate(userWithUpdatedData, (Apprentice) userToUpdate);
        }
        else if (userToUpdate instanceof Trainer) {
            partialUpdate(userWithUpdatedData, (Trainer) userToUpdate);
        }
        else {
            throw new IllegalArgumentException("Unknown User type");
        }
    }
}
