package de.icp.match.request.repository;

import de.icp.match.request.model.ExemptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExemptionRequestRepository extends JpaRepository<ExemptionRequest, UUID> {
}