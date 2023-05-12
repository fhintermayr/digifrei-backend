package de.icp.match.request.controller;

import de.icp.match.request.dto.ExemptionRequestDto;
import de.icp.match.request.dto.ExemptionRequestSubmissionDto;
import de.icp.match.request.mapper.ExemptionRequestMapper;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.service.SubmitRequestService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExemptionRequestController {

    private final ExemptionRequestMapper exemptionRequestMapper;
    private final SubmitRequestService submitRequestService;

    public ExemptionRequestController(ExemptionRequestMapper exemptionRequestMapper, SubmitRequestService submitRequestService) {
        this.exemptionRequestMapper = exemptionRequestMapper;
        this.submitRequestService = submitRequestService;
    }

    @PostMapping("exemption")
    public ResponseEntity<ExemptionRequestDto> submitRequest(@RequestBody @Valid ExemptionRequestSubmissionDto submittedRequestDto) {

        try {
            ExemptionRequest submittedRequest = exemptionRequestMapper.toEntity(submittedRequestDto);
            ExemptionRequest savedSubmission = submitRequestService.saveSubmission(submittedRequest);
            ExemptionRequestDto savedSubmissionDto = exemptionRequestMapper.toDto(savedSubmission);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedSubmissionDto);
        }
        catch (Exception exception) {
            log.error("Failed to save new exemption request: {}", exception.getMessage());
            return exception instanceof IllegalArgumentException ?
                    ResponseEntity.badRequest().build() :
                    ResponseEntity.internalServerError().build();
        }
    }

}
