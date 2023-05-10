package de.icp.match.user.mapper;


import de.icp.match.user.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateDto userCreationDto);

    @ObjectFactory
    default User createUser(UserCreateDto createDto) {
        if (createDto instanceof ApprenticeCreateDto )return new Apprentice(createDto.getFirstName(), createDto.getLastName(), createDto.getEmail(), createDto.getPassword(), new Department("f"),((ApprenticeCreateDto) createDto).getDoc());
        throw new RuntimeException("No matching object");
    }

}
