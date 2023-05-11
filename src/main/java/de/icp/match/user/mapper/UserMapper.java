package de.icp.match.user.mapper;


import de.icp.match.user.dto.ApprenticeCreateDto;
import de.icp.match.user.dto.TrainerCreationDto;
import de.icp.match.user.dto.UserCreateDto;
import de.icp.match.user.model.*;
import de.icp.match.user.service.DepartmentService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {DepartmentService.class})
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

    @Mapping(source = "departmentId", target = "department")
    Apprentice toApprentice(ApprenticeCreateDto apprenticeCreateDto);

    @Mapping(source = "departmentId", target = "department")

    Trainer toTrainer(TrainerCreationDto trainerCreationDto);

}
