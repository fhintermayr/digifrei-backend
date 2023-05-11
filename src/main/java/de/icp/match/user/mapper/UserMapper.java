package de.icp.match.user.mapper;


import de.icp.match.user.dto.ApprenticeCreateDto;
import de.icp.match.user.dto.TrainerCreationDto;
import de.icp.match.user.dto.UserCreateDto;
import de.icp.match.user.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("toUser")
    default User toUser(UserCreateDto userCreationDto) {
        if (userCreationDto.getUserType() == UserType.APPRENTICE) {
            return toApprentice((ApprenticeCreateDto) userCreationDto);
        }
        if (userCreationDto.getUserType() == UserType.TRAINER) {
            return toTrainer((TrainerCreationDto) userCreationDto);
        }
        else {
            throw new IllegalArgumentException("Unknown UserCreateDto type");
        }
    }

    @Mapping(source = "departmentId", target = "department.id")
    Apprentice toApprentice(ApprenticeCreateDto apprenticeCreateDto);

    @Mapping(source = "departmentId", target = "department.id")

    Trainer toTrainer(TrainerCreationDto trainerCreationDto);

}
