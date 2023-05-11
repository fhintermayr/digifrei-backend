package de.icp.match.user.mapper;

import de.icp.match.user.model.Apprentice;
import de.icp.match.user.model.UserUpdateDto;
import de.icp.match.user.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserUpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UserUpdateDto userWithUpdatedData, @MappingTarget Apprentice apprenticeToUpdate);

    default void partialUpdateUser(UserUpdateDto userWithUpdatedData, User userToUpdate) {
        if (userToUpdate instanceof Apprentice) {
            partialUpdate(userWithUpdatedData, (Apprentice) userToUpdate);
        } else {
            throw new IllegalArgumentException("Unknown User type");
        }
    }
}
