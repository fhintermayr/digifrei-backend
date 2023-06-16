package de.icp.match.request.controller;

import de.icp.match.request.dto.ExemptionRequestDto;
import de.icp.match.request.dto.ExemptionRequestSubmissionDto;
import de.icp.match.request.dto.ExemptionRequestUpdateDto;
import de.icp.match.request.mapper.ExemptionRequestMapper;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.service.RequestDeletionService;
import de.icp.match.request.service.RequestQueryService;
import de.icp.match.request.service.RequestUpdateService;
import de.icp.match.request.service.RequestSubmissionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class ExemptionRequestController {

    private final ExemptionRequestMapper exemptionRequestMapper;
    private final RequestSubmissionService requestSubmissionService;
    private final RequestQueryService requestQueryService;
    private final RequestUpdateService requestUpdateService;
    private final RequestDeletionService requestDeletionService;

    public ExemptionRequestController(ExemptionRequestMapper exemptionRequestMapper, RequestSubmissionService requestSubmissionService, RequestQueryService requestQueryService, RequestUpdateService requestUpdateService, RequestDeletionService requestDeletionService) {
        this.exemptionRequestMapper = exemptionRequestMapper;
        this.requestSubmissionService = requestSubmissionService;
        this.requestQueryService = requestQueryService;
        this.requestUpdateService = requestUpdateService;
        this.requestDeletionService = requestDeletionService;
    }

    @PostMapping("exemption")
    public ResponseEntity<ExemptionRequestDto> submitRequest(@RequestBody @Valid ExemptionRequestSubmissionDto submittedRequestDto) {

        ExemptionRequest submittedRequest = exemptionRequestMapper.toEntity(submittedRequestDto);
        ExemptionRequest savedSubmission = requestSubmissionService.saveSubmission(submittedRequest);
        ExemptionRequestDto savedSubmissionDto = exemptionRequestMapper.toDto(savedSubmission);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedSubmissionDto);
    }

    @GetMapping("exemption/{id}")
    public ResponseEntity<ExemptionRequestDto> getExemptionRequestById(@PathVariable Long id) {

        ExemptionRequest loadedRequest = requestQueryService.getRequestById(id);
        ExemptionRequestDto loadedRequestDto = exemptionRequestMapper.toDto(loadedRequest);

        return ResponseEntity.ok(loadedRequestDto);
    }

    @GetMapping("exemption/self")
    public ResponseEntity<Page<ExemptionRequestDto>> getAllSelfSubmittedRequests(@RequestParam(defaultValue = "0") Integer page,
                                                                                 @RequestParam(defaultValue = "10") Integer size) {

        Page<ExemptionRequest> selfSubmittedRequests = requestQueryService.loadSelfSubmittedRequests(page, size);
        Page<ExemptionRequestDto> selfSubmittedRequestsDto = selfSubmittedRequests.map(exemptionRequestMapper::toDto);

        return ResponseEntity.ok(selfSubmittedRequestsDto);
    }

    @GetMapping("exemption/own-department")
    public ResponseEntity<Page<ExemptionRequestDto>> getAllRequestOfTrainersDepartment(@RequestParam(defaultValue = "0") Integer page,
                                                                                       @RequestParam(defaultValue = "10") Integer size) {

        Page<ExemptionRequest> requestsOfAuthenticatedTrainersDepartment = requestQueryService.loadRequestsOfTrainersDepartment(page, size);
        Page<ExemptionRequestDto> trainersDepartmentRequestsDto = requestsOfAuthenticatedTrainersDepartment.map(exemptionRequestMapper::toDto);

        return ResponseEntity.ok(trainersDepartmentRequestsDto);
    }

    @PutMapping("exemption/{id}")
    public ResponseEntity<ExemptionRequestDto> updateExemptionRequestById(@RequestBody @Valid ExemptionRequestUpdateDto requestUpdateDto, @PathVariable Long id) {

        ExemptionRequest updatedExemptionRequest = requestUpdateService.updateRequestById(id, requestUpdateDto);
        ExemptionRequestDto updatedExemptionRequestDto = exemptionRequestMapper.toDto(updatedExemptionRequest);

        return ResponseEntity.ok(updatedExemptionRequestDto);
    }

    @DeleteMapping("exemption/{id}")
    public ResponseEntity<Void> deleteExemptionRequestById(@PathVariable Long id) {
        requestDeletionService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
