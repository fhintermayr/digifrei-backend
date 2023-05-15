package de.icp.match.request.service;

import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.repository.ExemptionRequestRepository;
import de.icp.match.security.service.CurrentUserService;
import de.icp.match.user.model.Apprentice;
import de.icp.match.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestQueryService {

    private final CurrentUserService currentUserService;
    private final ExemptionRequestRepository exemptionRequestRepository;

    public RequestQueryService(CurrentUserService currentUserService, ExemptionRequestRepository exemptionRequestRepository) {
        this.currentUserService = currentUserService;
        this.exemptionRequestRepository = exemptionRequestRepository;
    }

    public List<ExemptionRequest> loadSelfSubmittedRequests() throws ClassCastException {

        User authenticatedUser = currentUserService.getCurrentlyAuthenticatedUser();
        Apprentice authenticatedApprentice = (Apprentice) authenticatedUser;

        return authenticatedApprentice.getExemptionRequests();
    }

    public List<ExemptionRequest> loadRequestsOfTrainersDepartment() {

        Long authenticatedTrainersDepartmentId = currentUserService.getCurrentlyAuthenticatedUser().getDepartment().getId();
        return exemptionRequestRepository.findByApplicant_Department_Id(authenticatedTrainersDepartmentId);
    }
}
