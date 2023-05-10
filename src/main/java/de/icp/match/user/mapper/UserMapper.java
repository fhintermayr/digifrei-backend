package de.icp.match.user.mapper;

import de.icp.match.dto.UserCreationDto;
import de.icp.match.dto.UserDto;
import de.icp.match.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationDto userCreationDto);

    User toEntity(UserDto userDto);

    UserDto toDto(User user);

}
