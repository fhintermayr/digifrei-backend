package de.icp.match.user.mapper;

import de.icp.match.dto.UserCreationDto;
import de.icp.match.dto.UserDto;
import de.icp.match.user.model.User;
import de.icp.match.user.preferences.UserPreferencesMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserPreferencesMapper.class})
public interface UserMapper {


    User toUser(UserCreationDto userCreationDto);

    User toEntity(UserDto userDto);

    UserDto toDto(User user);


    List<UserDto> toDto(List<User> user);

    List<User> toEntity(List<UserDto> userDto);
}
