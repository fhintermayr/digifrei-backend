package de.icp.match.user.service;

import de.icp.match.user.model.SocioEduExpert;
import de.icp.match.user.model.SocioEduExpertRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SocioEduExpertService {

    private final SocioEduExpertRepository socioEduExpertRepository;

    public SocioEduExpertService(SocioEduExpertRepository socioEduExpertRepository) {
        this.socioEduExpertRepository = socioEduExpertRepository;
    }

    public SocioEduExpert findById(UUID uuid) {
        return socioEduExpertRepository.findById(uuid).orElseThrow(EntityNotFoundException::new);
    }
}
