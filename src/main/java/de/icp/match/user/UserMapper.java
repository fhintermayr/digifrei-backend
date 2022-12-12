package de.icp.match.user;

import de.icp.match.dto.UserCreationDto;
import de.icp.match.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
    User toUser(UserCreationDto userCreationDto);
}
