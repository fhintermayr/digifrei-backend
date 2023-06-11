package de.icp.match.request.service;

import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.model.ProcessingStatus;
import de.icp.match.request.repository.ExemptionRequestRepository;
import de.icp.match.security.service.CurrentUserService;
import de.icp.match.user.model.Apprentice;
import de.icp.match.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class RequestQueryService {

    private final CurrentUserService currentUserService;
    private final ExemptionRequestRepository exemptionRequestRepository;

    public RequestQueryService(CurrentUserService currentUserService, ExemptionRequestRepository exemptionRequestRepository) {
        this.currentUserService = currentUserService;
        this.exemptionRequestRepository = exemptionRequestRepository;
    }

    public ExemptionRequest getRequestById(Long id) {
        return exemptionRequestRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ExemptionRequest> loadSelfSubmittedRequests() throws ClassCastException {

        User authenticatedUser = currentUserService.getCurrentlyAuthenticatedUser();

        if (authenticatedUser instanceof Apprentice authenticatedApprentice) {
            return authenticatedApprentice.getExemptionRequests();
        }

        return Collections.emptyList();
    }

    public List<ExemptionRequest> loadRequestsOfTrainersDepartment() {

        Long authenticatedTrainersDepartmentId = currentUserService.getCurrentlyAuthenticatedUser().getDepartment().getId();
        return exemptionRequestRepository.findByApplicantDepartmentId(authenticatedTrainersDepartmentId);
    }

    public List<ExemptionRequest> getRequestsWithMissingConfirmation() {

        LocalDateTime dayBeforeYesterday = LocalDate.now().minusDays(2).atStartOfDay();

        return exemptionRequestRepository.findByRequestProcessingProcessingStatusAndEndTimeBefore(ProcessingStatus.APPROVED, dayBeforeYesterday);
    }
}
