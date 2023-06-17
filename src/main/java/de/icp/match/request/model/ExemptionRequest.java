package de.icp.match.request.model;

import de.icp.match.user.model.Apprentice;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ExemptionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @PastOrPresent
    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate = LocalDateTime.now();

    @Column(name = "reason", nullable = false)
    private String reason;

    @Enumerated
    @Column(name = "exemption_category", nullable = false)
    private ExemptionCategory exemptionCategory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Apprentice applicant;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "exemptionRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private RequestProcessing requestProcessing;

    public ExemptionRequest(LocalDateTime startTime, LocalDateTime endTime, String reason, ExemptionCategory exemptionCategory, Apprentice applicant) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.exemptionCategory = exemptionCategory;
        this.applicant = applicant;
    }

}