package de.icp.match.user.mapper;


import de.icp.match.user.dto.ApprenticeCreateDto;
import de.icp.match.user.dto.UserCreateDto;
import de.icp.match.user.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("toUser")
    default User toUser(UserCreateDto userCreationDto) {
        if (userCreationDto instanceof ApprenticeCreateDto) {
            return toApprentice((ApprenticeCreateDto) userCreationDto);
        } else {
            throw new IllegalArgumentException("Unknown UserCreateDto type");
        }
    }

    default Apprentice toApprentice(ApprenticeCreateDto apprenticeCreateDto) {
        return new Apprentice(
                apprenticeCreateDto.getFirstName(),
                apprenticeCreateDto.getLastName(),
                apprenticeCreateDto.getEmail(),
                apprenticeCreateDto.getPassword(),
                apprenticeCreateDto.getDepartment(),
                apprenticeCreateDto.getDoc()
        );
    }

}
