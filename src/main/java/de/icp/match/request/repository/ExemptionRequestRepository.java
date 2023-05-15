package de.icp.match.request.repository;

import de.icp.match.request.model.ExemptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemptionRequestRepository extends JpaRepository<ExemptionRequest, Long> {
    List<ExemptionRequest> findByApplicant_Department_Id(Long id);
}