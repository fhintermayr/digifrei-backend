package de.icp.match.user.mapper;

import de.icp.match.user.dto.socio_edu_expert.SocioEduExpertCreationDto;
import de.icp.match.user.model.SocioEduExpert;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SocioEduExpertMapper {
    SocioEduExpert toEntity(SocioEduExpertCreationDto socioEduExpertCreationDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SocioEduExpert partialUpdate(SocioEduExpertCreationDto socioEduExpertCreationDto, @MappingTarget SocioEduExpert socioEduExpert);
}