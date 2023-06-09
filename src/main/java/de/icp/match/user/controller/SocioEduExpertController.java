package de.icp.match.user.controller;

import de.icp.match.user.dto.socio_edu_expert.SocioEduExpertCreationDto;
import de.icp.match.user.mapper.SocioEduExpertMapper;
import de.icp.match.user.model.SocioEduExpert;
import de.icp.match.user.service.SocioEduExpertService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("socio-edu-expert")
public class SocioEduExpertController {

    private final SocioEduExpertService socioEduExpertService;
    private final SocioEduExpertMapper socioEduExpertMapper;

    public SocioEduExpertController(SocioEduExpertService socioEduExpertService,
                                    SocioEduExpertMapper socioEduExpertMapper) {
        this.socioEduExpertService = socioEduExpertService;
        this.socioEduExpertMapper = socioEduExpertMapper;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TRAINER')")
    public ResponseEntity<SocioEduExpert> registerSocioEduExpert(@RequestBody @Valid SocioEduExpertCreationDto creationDto) {

        SocioEduExpert socioEduExpert = socioEduExpertMapper.toEntity(creationDto);
        SocioEduExpert registeredSocioEduExpert = socioEduExpertService.registerSocioEduExpert(socioEduExpert);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredSocioEduExpert);
    }

    @GetMapping
    public ResponseEntity<List<SocioEduExpert>> getAllSocioEduExpertsContainingSearchTerm(@RequestParam(required = false) String searchTerm) {

        List<SocioEduExpert> foundSocioEduExperts = socioEduExpertService.findAllContainingSearchTerm(searchTerm);

        return ResponseEntity.ok(foundSocioEduExperts);
    }

    @GetMapping("{id}")
    public ResponseEntity<SocioEduExpert> getSocioEduExpert(@PathVariable Long id) {

        SocioEduExpert foundSocioEduExpert = socioEduExpertService.findById(id);

        return ResponseEntity.ok(foundSocioEduExpert);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('TRAINER')")
    public ResponseEntity<SocioEduExpert> updateSocioEduExpert(@PathVariable Long id, @RequestBody SocioEduExpertCreationDto updateDto) {

        SocioEduExpert updatedSocioEduExpert = socioEduExpertService.updateSocioEduExpert(id, updateDto);

        return ResponseEntity.ok(updatedSocioEduExpert);
    }
}
