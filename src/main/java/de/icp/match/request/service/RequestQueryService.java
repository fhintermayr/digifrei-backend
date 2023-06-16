package de.icp.match.request.service;

import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.model.ProcessingStatus;
import de.icp.match.request.repository.ExemptionRequestRepository;
import de.icp.match.security.service.CurrentUserService;
import de.icp.match.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public Page<ExemptionRequest> loadSelfSubmittedRequests(int page, int size) {

        User authenticatedUser = currentUserService.getCurrentlyAuthenticatedUser();

        Pageable pageable = PageRequest.of(page, size);

        return exemptionRequestRepository.findByApplicantIdOrderBySubmissionDateDesc(authenticatedUser.getId(), pageable);
    }

    public Page<ExemptionRequest> loadRequestsOfTrainersDepartment(int page, int size) {

        Long authenticatedTrainersDepartmentId = currentUserService.getCurrentlyAuthenticatedUser().getDepartment().getId();

        Pageable pageable = PageRequest.of(page, size, Sort.by("submissionDate"));

        return exemptionRequestRepository.findByApplicantDepartmentId(authenticatedTrainersDepartmentId, pageable);
    }

    public List<ExemptionRequest> getRequestsWithMissingConfirmation() {

        LocalDateTime dayBeforeYesterday = LocalDate.now().minusDays(2).atStartOfDay();

        return exemptionRequestRepository.findByRequestProcessingProcessingStatusAndEndTimeBefore(ProcessingStatus.APPROVED, dayBeforeYesterday);
    }
}
