package de.icp.match.user.service;

import de.icp.match.user.model.SocioEduExpert;
import de.icp.match.user.repository.SocioEduExpertRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SocioEduExpertService {

    private final SocioEduExpertRepository socioEduExpertRepository;

    public SocioEduExpertService(SocioEduExpertRepository socioEduExpertRepository) {
        this.socioEduExpertRepository = socioEduExpertRepository;
    }

    public SocioEduExpert findById(Long id) {
        return socioEduExpertRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
