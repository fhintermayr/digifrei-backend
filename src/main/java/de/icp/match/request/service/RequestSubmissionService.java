package de.icp.match.request.service;

import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.repository.ExemptionRequestRepository;
import de.icp.match.user.model.Apprentice;
import de.icp.match.user.service.UserQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RequestSubmissionService {

    @Value("${mail.enabled}")
    private boolean emailNotificationIsEnabled;

    private final ExemptionRequestRepository exemptionRequestRepository;
    private final UserQueryService userQueryService;
    private final EmailNotificationService emailNotificationService;

    public RequestSubmissionService(ExemptionRequestRepository exemptionRequestRepository, UserQueryService userQueryService, EmailNotificationService emailNotificationService) {
        this.exemptionRequestRepository = exemptionRequestRepository;
        this.userQueryService = userQueryService;
        this.emailNotificationService = emailNotificationService;
    }


    public ExemptionRequest saveSubmission(ExemptionRequest submission) throws IllegalArgumentException, EntityNotFoundException {

        validateSubmissionProperties(submission);
        ExemptionRequest savedSubmission = exemptionRequestRepository.save(submission);
        log.info("Created new exemption request#{}", savedSubmission.getId());

        if (emailNotificationIsEnabled) emailNotificationService.notifyAboutSubmission(savedSubmission);

        return savedSubmission;
    }

    private void validateSubmissionProperties(ExemptionRequest submission) {
        if (submission.getEndTime().isBefore(submission.getStartTime())) {
            throw new IllegalArgumentException("End time can't be before start time");
        }

        if (userDoesNotExist(submission.getApplicant())) {
            throw new EntityNotFoundException("Applicant does not exist");
        }
    }

    private boolean userDoesNotExist(Apprentice applicant) {
        return !userQueryService.doesUserExist(applicant);
    }

}
