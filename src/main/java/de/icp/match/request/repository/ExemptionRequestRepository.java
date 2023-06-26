package de.icp.match.request.repository;

import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.model.ProcessingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExemptionRequestRepository extends JpaRepository<ExemptionRequest, Long> {
    List<ExemptionRequest> findByRequestProcessingProcessingStatusAndEndTimeBefore(ProcessingStatus processingStatus, LocalDateTime endTime);
    Page<ExemptionRequest> findByApplicantDepartmentIdOrderBySubmissionDateDesc(Long id, Pageable pageable);
    Page<ExemptionRequest> findByApplicantIdOrderBySubmissionDateDesc(Integer id, Pageable pageable);
}