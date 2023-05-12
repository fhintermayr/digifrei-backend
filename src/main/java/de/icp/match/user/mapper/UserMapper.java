package de.icp.match.user.mapper;


import de.icp.match.user.dto.*;
import de.icp.match.user.model.*;
import de.icp.match.user.service.DepartmentService;
import de.icp.match.user.service.SocioEduExpertService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {DepartmentService.class, SocioEduExpertService.class})
public interface UserMapper {

    @Named("toUser")
    default User toUser(UserCreateDto userCreationDto) {
        if (userCreationDto.getUserType() == UserType.APPRENTICE) {
            return toApprentice((ApprenticeCreateDto) userCreationDto);
        } else if (userCreationDto.getUserType() == UserType.TRAINER) {
            return toTrainer((TrainerCreationDto) userCreationDto);
        } else {
            throw new IllegalArgumentException("Unknown UserCreateDto type");
        }
    }

    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "socioEduExpertId", target = "socioEduExpert")
    Apprentice toApprentice(ApprenticeCreateDto apprenticeCreateDto);

    @Mapping(source = "departmentId", target = "department")
    Trainer toTrainer(TrainerCreationDto trainerCreationDto);


    @Named("toDto")
    default UserDto toDto(User user) {
        if (user instanceof Apprentice) {
            return toDto((Apprentice) user);
        } else if (user instanceof Trainer) {
            return toDto((Trainer) user);
        } else {
            throw new IllegalArgumentException("Unknown User type");
        }
    }

    ApprenticeDto toDto(Apprentice apprentice);

    TrainerDto toDto(Trainer trainer);

}
