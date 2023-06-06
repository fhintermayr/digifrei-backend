package de.icp.match.request.service;

import de.icp.match.request.dto.ExemptionRequestUpdateDto;
import de.icp.match.request.mapper.ExemptionRequestMapperImpl;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.repository.ExemptionRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestUpdateService {

    private final RequestQueryService requestQueryService;
    private final ExemptionRequestMapperImpl exemptionRequestMapper;
    private final ExemptionRequestRepository exemptionRequestRepository;

    public RequestUpdateService(RequestQueryService requestQueryService, ExemptionRequestMapperImpl exemptionRequestMapper,
                                ExemptionRequestRepository exemptionRequestRepository) {
        this.requestQueryService = requestQueryService;
        this.exemptionRequestMapper = exemptionRequestMapper;
        this.exemptionRequestRepository = exemptionRequestRepository;
    }

    public ExemptionRequest updateRequestById(Long id, ExemptionRequestUpdateDto requestUpdateDto) {

        ExemptionRequest oldExemptionRequest = requestQueryService.getRequestById(id);
        ExemptionRequest updatedExemptionRequest = exemptionRequestMapper.partialUpdate(requestUpdateDto, oldExemptionRequest);

        if (updatedExemptionRequest.getEndTime().isBefore(updatedExemptionRequest.getStartTime())) {
            throw new IllegalArgumentException("End time can't be before start time");
        }

        return exemptionRequestRepository.save(updatedExemptionRequest);
    }
}
