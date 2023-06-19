package de.icp.match.user.service;

import de.icp.match.user.exception.DuplicateEmailException;
import de.icp.match.user.model.SocioEduExpert;
import de.icp.match.user.repository.SocioEduExpertRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SocioEduExpertService {

    private final SocioEduExpertRepository socioEduExpertRepository;

    public SocioEduExpertService(SocioEduExpertRepository socioEduExpertRepository) {
        this.socioEduExpertRepository = socioEduExpertRepository;
    }

    public SocioEduExpert registerSocioEduExpert(SocioEduExpert socioEduExpert) {
        try {
            return socioEduExpertRepository.save(socioEduExpert);
        }
        catch (DataIntegrityViolationException e) {

            String errorMessage = String.format("Die E-Mail %s wird bereits von einem anderen Fachdienst verwendet", socioEduExpert.getEmail());

            throw new DuplicateEmailException(errorMessage, e);
        }
    }

    public SocioEduExpert findById(Long id) {
        return socioEduExpertRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
