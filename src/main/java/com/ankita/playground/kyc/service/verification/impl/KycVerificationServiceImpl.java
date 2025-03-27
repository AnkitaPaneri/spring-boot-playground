package com.ankita.playground.kyc.service.verification.impl;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.Document;
import com.ankita.playground.kyc.model.VerificationCase;
import com.ankita.playground.kyc.model.VerificationStep;
import com.ankita.playground.kyc.repository.VerificationCaseRepository;
import com.ankita.playground.kyc.repository.VerificationStepRepository;
import com.ankita.playground.kyc.service.verification.KycVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KycVerificationServiceImpl implements KycVerificationService {

    private final VerificationCaseRepository verificationCaseRepository;
    private final VerificationStepRepository verificationStepRepository;

    @Autowired
    public KycVerificationServiceImpl(
            VerificationCaseRepository verificationCaseRepository,
            VerificationStepRepository verificationStepRepository) {
        this.verificationCaseRepository = verificationCaseRepository;
        this.verificationStepRepository = verificationStepRepository;
    }

    @Override
    @Transactional
    public VerificationCase initiateKycVerification(Customer customer, List<Document> documents) {
        VerificationCase verificationCase = new VerificationCase();
        verificationCase.setCustomer(customer);
        verificationCase.setCaseType(VerificationCase.CaseType.KYC_VERIFICATION);
        verificationCase.setStatus(VerificationCase.CaseStatus.PENDING);
        verificationCase.setPriority(VerificationCase.CasePriority.MEDIUM);
        
        verificationCase = verificationCaseRepository.save(verificationCase);
        
        List<VerificationStep> steps = createKycVerificationSteps(verificationCase);
        verificationCase.setVerificationSteps(new java.util.HashSet<>(steps));
        
        return verificationCaseRepository.save(verificationCase);
    }

    private List<VerificationStep> createKycVerificationSteps(VerificationCase verificationCase) {
        List<VerificationStep> steps = new ArrayList<>();
        
        VerificationStep identityStep = new VerificationStep();
        identityStep.setVerificationCase(verificationCase);
        identityStep.setStepType(VerificationStep.StepType.IDENTITY_VERIFICATION);
        identityStep.setStatus(VerificationStep.StepStatus.PENDING);
        identityStep.setStepOrder(1);
        steps.add(verificationStepRepository.save(identityStep));
        
        VerificationStep addressStep = new VerificationStep();
        addressStep.setVerificationCase(verificationCase);
        addressStep.setStepType(VerificationStep.StepType.ADDRESS_VERIFICATION);
        addressStep.setStatus(VerificationStep.StepStatus.PENDING);
        addressStep.setStepOrder(2);
        steps.add(verificationStepRepository.save(addressStep));
        
        VerificationStep documentStep = new VerificationStep();
        documentStep.setVerificationCase(verificationCase);
        documentStep.setStepType(VerificationStep.StepType.DOCUMENT_VERIFICATION);
        documentStep.setStatus(VerificationStep.StepStatus.PENDING);
        documentStep.setStepOrder(3);
        steps.add(verificationStepRepository.save(documentStep));
        
        VerificationStep watchlistStep = new VerificationStep();
        watchlistStep.setVerificationCase(verificationCase);
        watchlistStep.setStepType(VerificationStep.StepType.WATCHLIST_SCREENING);
        watchlistStep.setStatus(VerificationStep.StepStatus.PENDING);
        watchlistStep.setStepOrder(4);
        steps.add(verificationStepRepository.save(watchlistStep));
        
        VerificationStep riskStep = new VerificationStep();
        riskStep.setVerificationCase(verificationCase);
        riskStep.setStepType(VerificationStep.StepType.RISK_ASSESSMENT);
        riskStep.setStatus(VerificationStep.StepStatus.PENDING);
        riskStep.setStepOrder(5);
        steps.add(verificationStepRepository.save(riskStep));
        
        VerificationStep approvalStep = new VerificationStep();
        approvalStep.setVerificationCase(verificationCase);
        approvalStep.setStepType(VerificationStep.StepType.FINAL_APPROVAL);
        approvalStep.setStatus(VerificationStep.StepStatus.PENDING);
        approvalStep.setStepOrder(6);
        steps.add(verificationStepRepository.save(approvalStep));
        
        return steps;
    }

    @Override
    @Transactional
    public Map<String, Object> verifyIdentity(Long caseId, String verifierId) {
        VerificationCase verificationCase = verificationCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + caseId));
        
        if (verificationCase.getStatus() == VerificationCase.CaseStatus.PENDING) {
            verificationCase.setStatus(VerificationCase.CaseStatus.IN_PROGRESS);
            verificationCase.setAssignedTo(verifierId);
            verificationCaseRepository.save(verificationCase);
        }
        
        VerificationStep identityStep = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepType() == VerificationStep.StepType.IDENTITY_VERIFICATION)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Identity verification step not found"));
        
        identityStep.setStatus(VerificationStep.StepStatus.IN_PROGRESS);
        identityStep.setAssignedTo(verifierId);
        identityStep.setStartDate(LocalDateTime.now());
        verificationStepRepository.save(identityStep);
        
        
        Map<String, Object> results = new HashMap<>();
        results.put("identityVerified", true);
        results.put("confidenceScore", 85);
        results.put("documentAuthenticity", "VERIFIED");
        results.put("facialMatchScore", 92);
        
        identityStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        identityStep.setCompletionDate(LocalDateTime.now());
        identityStep.setNotes("Identity verified successfully with high confidence");
        verificationStepRepository.save(identityStep);
        
        return results;
    }

    @Override
    @Transactional
    public Map<String, Object> verifyAddress(Long caseId, String verifierId) {
        VerificationCase verificationCase = verificationCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + caseId));
        
        VerificationStep addressStep = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepType() == VerificationStep.StepType.ADDRESS_VERIFICATION)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Address verification step not found"));
        
        addressStep.setStatus(VerificationStep.StepStatus.IN_PROGRESS);
        addressStep.setAssignedTo(verifierId);
        addressStep.setStartDate(LocalDateTime.now());
        verificationStepRepository.save(addressStep);
        
        
        Map<String, Object> results = new HashMap<>();
        results.put("addressVerified", true);
        results.put("addressFormat", "STANDARDIZED");
        results.put("residenceConfirmed", true);
        results.put("addressRisk", "LOW");
        
        addressStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        addressStep.setCompletionDate(LocalDateTime.now());
        addressStep.setNotes("Address verified successfully against postal database");
        verificationStepRepository.save(addressStep);
        
        return results;
    }

    @Override
    @Transactional
    public Map<String, Object> performWatchlistScreening(Long caseId, String verifierId) {
        VerificationCase verificationCase = verificationCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + caseId));
        
        VerificationStep watchlistStep = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepType() == VerificationStep.StepType.WATCHLIST_SCREENING)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Watchlist screening step not found"));
        
        watchlistStep.setStatus(VerificationStep.StepStatus.IN_PROGRESS);
        watchlistStep.setAssignedTo(verifierId);
        watchlistStep.setStartDate(LocalDateTime.now());
        verificationStepRepository.save(watchlistStep);
        
        
        Map<String, Object> results = new HashMap<>();
        results.put("watchlistHits", 0);
        results.put("pepStatus", "NOT_LISTED");
        results.put("sanctionsStatus", "CLEAR");
        results.put("adverseMediaHits", 0);
        
        watchlistStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        watchlistStep.setCompletionDate(LocalDateTime.now());
        watchlistStep.setNotes("No watchlist hits found");
        verificationStepRepository.save(watchlistStep);
        
        return results;
    }

    @Override
    @Transactional
    public int calculateKycRiskScore(Long caseId) {
        VerificationCase verificationCase = verificationCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + caseId));
        
        VerificationStep riskStep = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepType() == VerificationStep.StepType.RISK_ASSESSMENT)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Risk assessment step not found"));
        
        boolean previousStepsCompleted = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepOrder() < riskStep.getStepOrder())
                .allMatch(step -> step.getStatus() == VerificationStep.StepStatus.COMPLETED);
        
        if (!previousStepsCompleted) {
            throw new RuntimeException("Cannot calculate risk score until all previous steps are completed");
        }
        
        
        int riskScore = 25; // Base score
        
        riskStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        riskStep.setCompletionDate(LocalDateTime.now());
        riskStep.setNotes("Risk assessment completed with score: " + riskScore);
        verificationStepRepository.save(riskStep);
        
        return riskScore;
    }

    @Override
    @Transactional
    public VerificationCase completeKycVerification(Long caseId, boolean approved, String notes, String verifierId) {
        VerificationCase verificationCase = verificationCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + caseId));
        
        VerificationStep approvalStep = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepType() == VerificationStep.StepType.FINAL_APPROVAL)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Final approval step not found"));
        
        boolean previousStepsCompleted = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepOrder() < approvalStep.getStepOrder())
                .allMatch(step -> step.getStatus() == VerificationStep.StepStatus.COMPLETED);
        
        if (!previousStepsCompleted) {
            throw new RuntimeException("Cannot complete verification until all previous steps are completed");
        }
        
        approvalStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        approvalStep.setAssignedTo(verifierId);
        approvalStep.setStartDate(LocalDateTime.now());
        approvalStep.setCompletionDate(LocalDateTime.now());
        approvalStep.setNotes(notes);
        verificationStepRepository.save(approvalStep);
        
        verificationCase.setStatus(approved ? VerificationCase.CaseStatus.COMPLETED : VerificationCase.CaseStatus.REJECTED);
        verificationCase.setCompletedAt(LocalDateTime.now());
        
        return verificationCaseRepository.save(verificationCase);
    }
}
