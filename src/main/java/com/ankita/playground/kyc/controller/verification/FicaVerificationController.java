package com.ankita.playground.kyc.controller.verification;

import com.ankita.playground.kyc.dto.request.verification.FicaVerificationRequest;
import com.ankita.playground.kyc.dto.response.verification.FicaVerificationResponse;
import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.Document;
import com.ankita.playground.kyc.model.VerificationCase;
import com.ankita.playground.kyc.service.CustomerService;
import com.ankita.playground.kyc.service.DocumentService;
import com.ankita.playground.kyc.service.verification.FicaVerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/verification/fica")
public class FicaVerificationController {

    private final FicaVerificationService ficaVerificationService;
    private final CustomerService customerService;
    private final DocumentService documentService;

    @Autowired
    public FicaVerificationController(
            FicaVerificationService ficaVerificationService,
            CustomerService customerService,
            DocumentService documentService) {
        this.ficaVerificationService = ficaVerificationService;
        this.customerService = customerService;
        this.documentService = documentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<FicaVerificationResponse> initiateFicaVerification(
            @Valid @RequestBody FicaVerificationRequest request,
            Authentication authentication) {
        
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        
        List<Document> documents = request.getDocumentIds() != null ?
                request.getDocumentIds().stream()
                        .map(documentService::getDocumentById)
                        .collect(Collectors.toList()) :
                List.of();
        
        VerificationCase verificationCase = ficaVerificationService.initiateFicaVerification(customer, documents);
        
        FicaVerificationResponse response = FicaVerificationResponse.fromVerificationCase(verificationCase);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{caseId}/verify-financial-information")
    public ResponseEntity<Map<String, Object>> verifyFinancialInformation(
            @PathVariable Long caseId,
            Authentication authentication) {
        
        String verifierId = authentication.getName();
        Map<String, Object> results = ficaVerificationService.verifyFinancialInformation(caseId, verifierId);
        
        return ResponseEntity.ok(results);
    }

    @PostMapping("/{caseId}/verify-income-sources")
    public ResponseEntity<Map<String, Object>> verifyIncomeSources(
            @PathVariable Long caseId,
            Authentication authentication) {
        
        String verifierId = authentication.getName();
        Map<String, Object> results = ficaVerificationService.verifyIncomeSources(caseId, verifierId);
        
        return ResponseEntity.ok(results);
    }

    @PostMapping("/{caseId}/verify-transaction-history")
    public ResponseEntity<Map<String, Object>> verifyTransactionHistory(
            @PathVariable Long caseId,
            Authentication authentication) {
        
        String verifierId = authentication.getName();
        Map<String, Object> results = ficaVerificationService.verifyTransactionHistory(caseId, verifierId);
        
        return ResponseEntity.ok(results);
    }

    @PostMapping("/{caseId}/aml-screening")
    public ResponseEntity<Map<String, Object>> performAmlScreening(
            @PathVariable Long caseId,
            Authentication authentication) {
        
        String verifierId = authentication.getName();
        Map<String, Object> results = ficaVerificationService.performAmlScreening(caseId, verifierId);
        
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{caseId}/risk-score")
    public ResponseEntity<Integer> calculateRiskScore(@PathVariable Long caseId) {
        int riskScore = ficaVerificationService.calculateFicaRiskScore(caseId);
        return ResponseEntity.ok(riskScore);
    }

    @PostMapping("/{caseId}/complete")
    public ResponseEntity<FicaVerificationResponse> completeVerification(
            @PathVariable Long caseId,
            @RequestParam boolean approved,
            @RequestParam(required = false) String notes,
            Authentication authentication) {
        
        String verifierId = authentication.getName();
        VerificationCase verificationCase = ficaVerificationService.completeFicaVerification(
                caseId, approved, notes, verifierId);
        
        FicaVerificationResponse response = FicaVerificationResponse.fromVerificationCase(verificationCase);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<FicaVerificationResponse> getVerificationCase(@PathVariable Long caseId) {
        VerificationCase verificationCase = ficaVerificationService.getVerificationCaseById(caseId);
        FicaVerificationResponse response = FicaVerificationResponse.fromVerificationCase(verificationCase);
        return ResponseEntity.ok(response);
    }
}
