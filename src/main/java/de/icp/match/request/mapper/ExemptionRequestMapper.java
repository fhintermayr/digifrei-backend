package de.icp.match.request.mapper;

import de.icp.match.request.dto.ExemptionRequestDto;
import de.icp.match.request.dto.ExemptionRequestSubmissionDto;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.model.RequestProcessing;
import de.icp.match.user.mapper.UserMapper;
import de.icp.match.user.service.UserQueryService;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, RequestProcessingMapper.class, UserQueryService.class}
)
public interface ExemptionRequestMapper {

    ExemptionRequest toEntity(ExemptionRequestDto exemptionRequestDto);

    ExemptionRequestDto toDto(ExemptionRequest exemptionRequest);

    List<ExemptionRequestDto> toDto(List<ExemptionRequest> exemptionRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ExemptionRequest partialUpdate(ExemptionRequestDto exemptionRequestDto, @MappingTarget ExemptionRequest exemptionRequest);

    @AfterMapping
    default void linkRequestProcessing(@MappingTarget ExemptionRequest exemptionRequest) {
        RequestProcessing requestProcessing = exemptionRequest.getRequestProcessing();
        if (requestProcessing != null) {
            requestProcessing.setExemptionRequest(exemptionRequest);
        }
    }

    @Mapping(source = "applicantId", target = "applicant")
    ExemptionRequest toEntity(ExemptionRequestSubmissionDto exemptionRequestSubmissionDto);
}