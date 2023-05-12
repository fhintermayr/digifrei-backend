package de.icp.match.request.service;

import de.icp.match.request.model.ExemptionCategory;
import de.icp.match.request.repository.ExemptionCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExemptionCategoryService {


    private final ExemptionCategoryRepository exemptionCategoryRepository;

    public ExemptionCategoryService(ExemptionCategoryRepository exemptionCategoryRepository) {
        this.exemptionCategoryRepository = exemptionCategoryRepository;
    }

    public ExemptionCategory findById(Integer id) {
        return exemptionCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
