package de.icp.match.request.mapper;

import de.icp.match.request.dto.RequestProcessingDto;
import de.icp.match.request.model.RequestProcessing;
import de.icp.match.user.mapper.UserMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface RequestProcessingMapper {
    RequestProcessing toEntity(RequestProcessingDto requestProcessingDto);

    RequestProcessingDto toDto(RequestProcessing requestProcessing);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RequestProcessing partialUpdate(RequestProcessingDto requestProcessingDto, @MappingTarget RequestProcessing requestProcessing);
}