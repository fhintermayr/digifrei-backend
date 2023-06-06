package de.icp.match.request.controller;

import de.icp.match.request.dto.ExemptionRequestDto;
import de.icp.match.request.dto.ExemptionRequestSubmissionDto;
import de.icp.match.request.mapper.ExemptionRequestMapper;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.service.RequestQueryService;
import de.icp.match.request.service.SubmitRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ExemptionRequestController {

    private final ExemptionRequestMapper exemptionRequestMapper;
    private final SubmitRequestService submitRequestService;
    private final RequestQueryService requestQueryService;

    public ExemptionRequestController(ExemptionRequestMapper exemptionRequestMapper, SubmitRequestService submitRequestService, RequestQueryService requestQueryService) {
        this.exemptionRequestMapper = exemptionRequestMapper;
        this.submitRequestService = submitRequestService;
        this.requestQueryService = requestQueryService;
    }

    @PostMapping("exemption")
    public ResponseEntity<ExemptionRequestDto> submitRequest(@RequestBody @Valid ExemptionRequestSubmissionDto submittedRequestDto) {

        ExemptionRequest submittedRequest = exemptionRequestMapper.toEntity(submittedRequestDto);
        ExemptionRequest savedSubmission = submitRequestService.saveSubmission(submittedRequest);
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
    public ResponseEntity<List<ExemptionRequestDto>> getAllSelfSubmittedRequests() {

        List<ExemptionRequest> selfSubmittedRequests = requestQueryService.loadSelfSubmittedRequests();
        List<ExemptionRequestDto> selfSubmittedRequestsDto = exemptionRequestMapper.toDto(selfSubmittedRequests);

        return ResponseEntity.ok(selfSubmittedRequestsDto);
    }

    @GetMapping("exemption/own-department")
    public ResponseEntity<List<ExemptionRequestDto>> getAllRequestOfTrainersDepartment() {

        List<ExemptionRequest> requestsOfAuthenticatedTrainersDepartment = requestQueryService.loadRequestsOfTrainersDepartment();
        List<ExemptionRequestDto> trainersDepartmentRequestsDto = exemptionRequestMapper.toDto(requestsOfAuthenticatedTrainersDepartment);

        return ResponseEntity.ok(trainersDepartmentRequestsDto);
    }

}
