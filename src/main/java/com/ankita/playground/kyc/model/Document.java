package com.ankita.playground.kyc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotBlank(message = "Document type is required")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @NotBlank(message = "File name is required")
    private String fileName;

    private String fileType;

    private String filePath;

    private Long fileSize;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private String verificationNotes;

    private String verifiedBy;

    private LocalDateTime verificationDate;

    private LocalDateTime uploadDate;

    @PrePersist
    protected void onCreate() {
        uploadDate = LocalDateTime.now();
        status = DocumentStatus.UPLOADED;
    }

    public enum DocumentType {
        ID_DOCUMENT,
        PASSPORT,
        DRIVERS_LICENSE,
        PROOF_OF_ADDRESS,
        BANK_STATEMENT,
        TAX_RETURN,
        INCOME_VERIFICATION,
        EMPLOYMENT_VERIFICATION,
        PROPERTY_DOCUMENT,
        OTHER
    }

    public enum DocumentStatus {
        UPLOADED,
        PENDING_VERIFICATION,
        VERIFIED,
        REJECTED,
        EXPIRED
    }
}
