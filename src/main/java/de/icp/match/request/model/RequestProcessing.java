package de.icp.match.request.model;

import de.icp.match.user.model.Trainer;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "request_processing")
public class RequestProcessing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "exemption_request_id", nullable = false)
    private ExemptionRequest exemptionRequest;

    @Enumerated
    @Column(name = "processing_status", nullable = false)
    private ProcessingStatus processingStatus;

    @Column(name = "comment")
    private String comment;

    @FutureOrPresent
    @Column(name = "processing_date", nullable = false)
    private LocalDateTime processing_date = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "processor_id", nullable = false)
    private Trainer processor;

    public RequestProcessing(ExemptionRequest exemptionRequest, ProcessingStatus processingStatus, String comment, Trainer processor) {
        this.exemptionRequest = exemptionRequest;
        this.processingStatus = processingStatus;
        this.comment = comment;
        this.processor = processor;
    }
}