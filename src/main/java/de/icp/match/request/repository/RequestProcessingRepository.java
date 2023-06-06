package de.icp.match.request.repository;

import de.icp.match.request.model.RequestProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestProcessingRepository extends JpaRepository<RequestProcessing, Long> {
}
