package de.icp.match.request.service;

import de.icp.match.request.dto.RequestProcessingUpdateDto;
import de.icp.match.request.mapper.RequestProcessingMapper;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.model.RequestProcessing;
import de.icp.match.request.repository.ExemptionRequestRepository;
import de.icp.match.request.repository.RequestProcessingRepository;
import de.icp.match.security.service.CurrentUserService;
import de.icp.match.user.model.Trainer;
import de.icp.match.user.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RequestProcessingService {

    private final RequestQueryService requestQueryService;
    private final CurrentUserService currentUserService;
    private final RequestProcessingRepository requestProcessingRepository;
    private final RequestProcessingMapper requestProcessingMapper;
    private final ExemptionRequestRepository exemptionRequestRepository;

    public RequestProcessingService(RequestQueryService requestQueryService, CurrentUserService currentUserService,
                                    RequestProcessingRepository requestProcessingRepository,
                                    RequestProcessingMapper requestProcessingMapper,
                                    ExemptionRequestRepository exemptionRequestRepository) {
        this.requestQueryService = requestQueryService;
        this.currentUserService = currentUserService;
        this.requestProcessingRepository = requestProcessingRepository;
        this.requestProcessingMapper = requestProcessingMapper;
        this.exemptionRequestRepository = exemptionRequestRepository;
    }

    public ExemptionRequest processExemptionRequest(Long exemptionRequestId, RequestProcessingUpdateDto processingUpdateDto) {

        User processor = currentUserService.getCurrentlyAuthenticatedUser();
        if (!(processor instanceof Trainer)) throw new IllegalArgumentException("A non trainer tried to process an exemption request");

        ExemptionRequest exemptionRequest = requestQueryService.getRequestById(exemptionRequestId);

        RequestProcessing requestProcessing = new RequestProcessing(
                exemptionRequest,
                processingUpdateDto.processingStatus(),
                processingUpdateDto.comment(),
                (Trainer) processor
        );

        RequestProcessing savedRequestProcessing = requestProcessingRepository.save(requestProcessing);

        return savedRequestProcessing.getExemptionRequest();
    }

    public ExemptionRequest updateRequestProcessing(Long exemptionRequestId, RequestProcessingUpdateDto processingUpdateDto) {

        User processor = currentUserService.getCurrentlyAuthenticatedUser();
        if (!(processor instanceof Trainer)) throw new IllegalArgumentException("A non trainer tried to process an exemption request");

        ExemptionRequest exemptionRequest = requestQueryService.getRequestById(exemptionRequestId);

        RequestProcessing oldRequestProcessing = exemptionRequest.getRequestProcessing();
        RequestProcessing updatedRequestProcessing = requestProcessingMapper.partialUpdate(processingUpdateDto, oldRequestProcessing);

        updatedRequestProcessing.setProcessor((Trainer) processor);
        updatedRequestProcessing.setProcessingDate(LocalDateTime.now());

        exemptionRequest.setRequestProcessing(updatedRequestProcessing);

        return exemptionRequestRepository.save(exemptionRequest);
    }

    public void withdrawExemptionRequestProcessing(Long exemptionRequestId) {

        ExemptionRequest exemptionRequest = requestQueryService.getRequestById(exemptionRequestId);
        exemptionRequest.setRequestProcessing(null);

        exemptionRequestRepository.save(exemptionRequest);
    }

}
