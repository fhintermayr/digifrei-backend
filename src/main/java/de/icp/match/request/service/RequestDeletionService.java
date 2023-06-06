package de.icp.match.request.service;

import de.icp.match.request.repository.ExemptionRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RequestDeletionService {

    private final ExemptionRequestRepository exemptionRequestRepository;

    public RequestDeletionService(ExemptionRequestRepository exemptionRequestRepository) {
        this.exemptionRequestRepository = exemptionRequestRepository;
    }

    public void deleteById(Long id) {

        var notFoundErrorMessage = String.format("Attempting to delete non-existent exemption request with id %d", id);

        if (!exemptionRequestRepository.existsById(id)) throw new EntityNotFoundException(notFoundErrorMessage);

        exemptionRequestRepository.deleteById(id);
    }

}
