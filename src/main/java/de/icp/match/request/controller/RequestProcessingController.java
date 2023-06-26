package de.icp.match.request.controller;

import de.icp.match.request.dto.ExemptionRequestDto;
import de.icp.match.request.dto.RequestProcessingUpdateDto;
import de.icp.match.request.mapper.ExemptionRequestMapper;
import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.service.RequestProcessingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("exemption/{id}/processing")
public class RequestProcessingController {

    private final RequestProcessingService requestProcessingService;
    private final ExemptionRequestMapper exemptionRequestMapper;

    public RequestProcessingController(RequestProcessingService requestProcessingService,
                                       ExemptionRequestMapper exemptionRequestMapper) {
        this.requestProcessingService = requestProcessingService;
        this.exemptionRequestMapper = exemptionRequestMapper;
    }

    @PostMapping
    public ResponseEntity<ExemptionRequestDto> processExemptionRequest(@PathVariable Long id, @RequestBody @Valid RequestProcessingUpdateDto processingUpdateDto) {

        ExemptionRequest processedExemptionRequest = requestProcessingService.processExemptionRequest(id, processingUpdateDto);
        ExemptionRequestDto processedExemptionRequestDto = exemptionRequestMapper.toDto(processedExemptionRequest);

        return ResponseEntity.ok(processedExemptionRequestDto);
    }

    @PutMapping
    public ResponseEntity<ExemptionRequestDto> updateExemptionRequestProcessing(@PathVariable Long id, @RequestBody @Valid RequestProcessingUpdateDto processingUpdateDto) {

        ExemptionRequest processedExemptionRequest = requestProcessingService.updateRequestProcessing(id, processingUpdateDto);
        ExemptionRequestDto processedExemptionRequestDto = exemptionRequestMapper.toDto(processedExemptionRequest);

        return ResponseEntity.ok(processedExemptionRequestDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> withdrawExemptionRequestProcessing(@PathVariable Long id) {

        requestProcessingService.withdrawExemptionRequestProcessing(id);

        return ResponseEntity.noContent().build();
    }

}
