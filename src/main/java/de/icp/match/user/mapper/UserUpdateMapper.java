package de.icp.match.user.mapper;

import de.icp.match.user.model.Apprentice;
import de.icp.match.user.dto.UserUpdateDto;
import de.icp.match.user.model.Trainer;
import de.icp.match.user.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserUpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UserUpdateDto userWithUpdatedData, @MappingTarget Apprentice apprenticeToUpdate);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UserUpdateDto userWithUpdatedData, @MappingTarget Trainer trainerToUpdate);

    default void partialUpdateUser(UserUpdateDto userWithUpdatedData, User userToUpdate) {
        if (userToUpdate instanceof Apprentice) {
            partialUpdate(userWithUpdatedData, (Apprentice) userToUpdate);
        }
        if (userToUpdate instanceof Trainer) {
            partialUpdate(userWithUpdatedData, (Trainer) userToUpdate);
        }
        else {
            throw new IllegalArgumentException("Unknown User type");
        }
    }
}
