package de.icp.match.request.repository;

import de.icp.match.request.model.ExemptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExemptionRequestRepository extends JpaRepository<ExemptionRequest, UUID> {
}