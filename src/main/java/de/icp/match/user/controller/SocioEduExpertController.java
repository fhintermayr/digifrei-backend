package de.icp.match.user.controller;

import de.icp.match.user.dto.socio_edu_expert.SocioEduExpertCreationDto;
import de.icp.match.user.mapper.SocioEduExpertMapper;
import de.icp.match.user.model.SocioEduExpert;
import de.icp.match.user.service.SocioEduExpertService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class SocioEduExpertController {

    private final SocioEduExpertService socioEduExpertService;
    private final SocioEduExpertMapper socioEduExpertMapper;

    public SocioEduExpertController(SocioEduExpertService socioEduExpertService,
                                    SocioEduExpertMapper socioEduExpertMapper) {
        this.socioEduExpertService = socioEduExpertService;
        this.socioEduExpertMapper = socioEduExpertMapper;
    }

    @PostMapping("socio-edu-expert")
    public ResponseEntity<SocioEduExpert> registerSocioEduExpert(@RequestBody @Valid SocioEduExpertCreationDto creationDto) {

        SocioEduExpert socioEduExpert = socioEduExpertMapper.toEntity(creationDto);
        SocioEduExpert registeredSocioEduExpert = socioEduExpertService.registerSocioEduExpert(socioEduExpert);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredSocioEduExpert);
    }

    @GetMapping("socio-edu-expert")
    public ResponseEntity<List<SocioEduExpert>> getAllSocioEduExpertsContainingSearchTerm(@RequestParam(required = false) String searchTerm) {

        List<SocioEduExpert> foundSocioEduExperts = socioEduExpertService.findAllContainingSearchTerm(searchTerm);

        return ResponseEntity.ok(foundSocioEduExperts);
    }

    @PutMapping("socio-edu-expert/{id}")
    public ResponseEntity<SocioEduExpert> updateSocioEduExpert(@PathVariable Long id, @RequestBody SocioEduExpertCreationDto updateDto) {

        SocioEduExpert updatedSocioEduExpert = socioEduExpertService.updateSocioEduExpert(id, updateDto);

        return ResponseEntity.ok(updatedSocioEduExpert);
    }
}
