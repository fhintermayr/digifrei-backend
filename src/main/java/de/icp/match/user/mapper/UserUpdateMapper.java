package de.icp.match.user.mapper;

import de.icp.match.dto.UserUpdateDto;
import de.icp.match.user.model.User;
import de.icp.match.user.preferences.UserPreferencesMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {UserPreferencesMapper.class})
public interface UserUpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserUpdateDto userWithUpdatedData, @MappingTarget User userToUpdate);

}
