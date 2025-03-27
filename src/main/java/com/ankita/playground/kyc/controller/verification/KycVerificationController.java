package com.ankita.playground.kyc.controller.verification;

import com.ankita.playground.kyc.dto.request.verification.KycVerificationRequest;
import com.ankita.playground.kyc.dto.response.verification.KycVerificationResponse;
import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.Document;
import com.ankita.playground.kyc.model.VerificationCase;
import com.ankita.playground.kyc.service.CustomerService;
import com.ankita.playground.kyc.service.DocumentService;
import com.ankita.playground.kyc.service.verification.KycVerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/verification/kyc")
public class KycVerificationController {

    private final KycVerificationService kycVerificationService;
    private final CustomerService customerService;
    private final DocumentService documentService;

    @Autowired
    public KycVerificationController(
            KycVerificationService kycVerificationService,
            CustomerService customerService,
            DocumentService documentService) {
        this.kycVerificationService = kycVerificationService;
        this.customerService = customerService;
        this.documentService = documentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<KycVerificationResponse> initiateKycVerification(
            @Valid @RequestBody KycVerificationRequest request,
            Authentication authentication) {
        
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        
        List<Document> documents = request.getDocumentIds() != null ?
                request.getDocumentIds().stream()
                        .map(documentService::getDocumentById)
                        .collect(Collectors.toList()) :
                List.of();
        
        VerificationCase verificationCase = kycVerificationService.initiateKycVerification(customer, documents);
        
        KycVerificationResponse response = KycVerificationResponse.fromVerificationCase(verificationCase);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{caseId}/verify-identity")
    public ResponseEntity<Map<String, Object>> verifyIdentity(
            @PathVariable Long caseId,
            Authentication authentication) {
        
        String verifierId = authentication.getName();
        Map<String, Object> results = kycVerificationService.verifyIdentity(caseId, verifierId);
        
        return ResponseEntity.ok(results);
    }

    @PostMapping("/{caseId}/verify-address")
    public ResponseEntity<Map<String, Object>> verifyAddress(
            @PathVariable Long caseId,
            Authentication authentication) {
        
        String verifierId = authentication.getName();
        Map<String, Object> results = kycVerificationService.verifyAddress(caseId, verifierId);
        
        return ResponseEntity.ok(results);
    }

    @PostMapping("/{caseId}/watchlist-screening")
    public ResponseEntity<Map<String, Object>> performWatchlistScreening(
            @PathVariable Long caseId,
            Authentication authentication) {
        
        String verifierId = authentication.getName();
        Map<String, Object> results = kycVerificationService.performWatchlistScreening(caseId, verifierId);
        
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{caseId}/risk-score")
    public ResponseEntity<Integer> calculateRiskScore(@PathVariable Long caseId) {
        int riskScore = kycVerificationService.calculateKycRiskScore(caseId);
        return ResponseEntity.ok(riskScore);
    }

    @PostMapping("/{caseId}/complete")
    public ResponseEntity<KycVerificationResponse> completeVerification(
            @PathVariable Long caseId,
            @RequestParam boolean approved,
            @RequestParam(required = false) String notes,
            Authentication authentication) {
        
        String verifierId = authentication.getName();
        VerificationCase verificationCase = kycVerificationService.completeKycVerification(
                caseId, approved, notes, verifierId);
        
        KycVerificationResponse response = KycVerificationResponse.fromVerificationCase(verificationCase);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<KycVerificationResponse> getVerificationCase(@PathVariable Long caseId) {
        VerificationCase verificationCase = kycVerificationService.getVerificationCaseById(caseId);
        KycVerificationResponse response = KycVerificationResponse.fromVerificationCase(verificationCase);
        return ResponseEntity.ok(response);
    }
}
