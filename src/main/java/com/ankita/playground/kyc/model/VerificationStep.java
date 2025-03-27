package com.ankita.playground.kyc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_steps")
public class VerificationStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Verification case is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verification_case_id")
    private VerificationCase verificationCase;

    @Enumerated(EnumType.STRING)
    private StepType stepType;

    @Enumerated(EnumType.STRING)
    private StepStatus status;

    private String notes;

    private String assignedTo;

    private LocalDateTime startDate;

    private LocalDateTime completionDate;

    private Integer stepOrder;

    @PrePersist
    protected void onCreate() {
        startDate = LocalDateTime.now();
        status = StepStatus.PENDING;
    }

    public enum StepType {
        IDENTITY_VERIFICATION,
        DOCUMENT_VERIFICATION,
        ADDRESS_VERIFICATION,
        FINANCIAL_VERIFICATION,
        EMPLOYMENT_VERIFICATION,
        PROPERTY_VERIFICATION,
        RISK_ASSESSMENT,
        WATCHLIST_SCREENING,
        FINAL_APPROVAL
    }

    public enum StepStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        FAILED,
        SKIPPED
    }
}
