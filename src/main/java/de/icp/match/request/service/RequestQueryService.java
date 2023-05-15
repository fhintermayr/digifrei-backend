package de.icp.match.request.service;

import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.security.service.CurrentUserService;
import de.icp.match.user.model.Apprentice;
import de.icp.match.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestQueryService {

    private final CurrentUserService currentUserService;

    public RequestQueryService(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    public List<ExemptionRequest> loadSelfSubmittedRequests() throws ClassCastException {

        User authenticatedUser = currentUserService.getCurrentlyAuthenticatedUser();
        Apprentice authenticatedApprentice = (Apprentice) authenticatedUser;

        return authenticatedApprentice.getExemptionRequests();
    }
}
