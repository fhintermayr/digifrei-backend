package de.icp.match.request.mapper;

import de.icp.match.request.dto.ExemptionRequestDto;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.model.RequestProcessing;
import de.icp.match.request.service.ExemptionCategoryService;
import de.icp.match.user.mapper.UserMapper;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, RequestProcessingMapper.class, ExemptionCategoryService.class}
)
public interface ExemptionRequestMapper {
    ExemptionRequest toEntity(ExemptionRequestDto exemptionRequestDto);

    ExemptionRequestDto toDto(ExemptionRequest exemptionRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ExemptionRequest partialUpdate(ExemptionRequestDto exemptionRequestDto, @MappingTarget ExemptionRequest exemptionRequest);

    @AfterMapping
    default void linkRequestProcessing(@MappingTarget ExemptionRequest exemptionRequest) {
        RequestProcessing requestProcessing = exemptionRequest.getRequestProcessing();
        if (requestProcessing != null) {
            requestProcessing.setExemptionRequest(exemptionRequest);
        }
    }
}