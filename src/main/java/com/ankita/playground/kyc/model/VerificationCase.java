package com.ankita.playground.kyc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_cases")
public class VerificationCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(unique = true)
    private String caseNumber;

    @Enumerated(EnumType.STRING)
    private CaseType caseType;

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    @Enumerated(EnumType.STRING)
    private CasePriority priority;

    private String assignedTo;

    @OneToMany(mappedBy = "verificationCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VerificationStep> verificationSteps = new HashSet<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = CaseStatus.PENDING;
        
        if (caseNumber == null) {
            caseNumber = "KYC-" + LocalDateTime.now().getYear() + "-" 
                + String.format("%06d", System.nanoTime() % 1000000);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        
        if (status == CaseStatus.COMPLETED && completedAt == null) {
            completedAt = LocalDateTime.now();
        }
    }

    public enum CaseType {
        KYC_VERIFICATION,
        FICA_VERIFICATION,
        MORTGAGE_VERIFICATION,
        IDENTITY_VERIFICATION,
        FINANCIAL_VERIFICATION,
        COMBINED_VERIFICATION
    }

    public enum CaseStatus {
        PENDING,
        IN_PROGRESS,
        UNDER_REVIEW,
        COMPLETED,
        REJECTED,
        ON_HOLD
    }

    public enum CasePriority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }
}
