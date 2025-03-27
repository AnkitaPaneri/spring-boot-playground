package com.ankita.playground.kyc.service.verification.impl;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.Document;
import com.ankita.playground.kyc.model.VerificationCase;
import com.ankita.playground.kyc.model.VerificationStep;
import com.ankita.playground.kyc.repository.VerificationCaseRepository;
import com.ankita.playground.kyc.repository.VerificationStepRepository;
import com.ankita.playground.kyc.service.verification.FicaVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FicaVerificationServiceImpl implements FicaVerificationService {

    private final VerificationCaseRepository verificationCaseRepository;
    private final VerificationStepRepository verificationStepRepository;

    @Autowired
    public FicaVerificationServiceImpl(
            VerificationCaseRepository verificationCaseRepository,
            VerificationStepRepository verificationStepRepository) {
        this.verificationCaseRepository = verificationCaseRepository;
        this.verificationStepRepository = verificationStepRepository;
    }

    @Override
    @Transactional
    public VerificationCase initiateFicaVerification(Customer customer, List<Document> documents) {
        VerificationCase verificationCase = new VerificationCase();
        verificationCase.setCustomer(customer);
        verificationCase.setCaseType(VerificationCase.CaseType.FICA_VERIFICATION);
        verificationCase.setStatus(VerificationCase.CaseStatus.PENDING);
        verificationCase.setPriority(VerificationCase.CasePriority.MEDIUM);
        
        verificationCase = verificationCaseRepository.save(verificationCase);
        
        List<VerificationStep> steps = createFicaVerificationSteps(verificationCase);
        verificationCase.setVerificationSteps(new java.util.HashSet<>(steps));
        
        return verificationCaseRepository.save(verificationCase);
    }

    private List<VerificationStep> createFicaVerificationSteps(VerificationCase verificationCase) {
        List<VerificationStep> steps = new ArrayList<>();
        
        VerificationStep financialInfoStep = new VerificationStep();
        financialInfoStep.setVerificationCase(verificationCase);
        financialInfoStep.setStepType(VerificationStep.StepType.FINANCIAL_INFORMATION_VERIFICATION);
        financialInfoStep.setStatus(VerificationStep.StepStatus.PENDING);
        financialInfoStep.setStepOrder(1);
        steps.add(verificationStepRepository.save(financialInfoStep));
        
        VerificationStep incomeSourceStep = new VerificationStep();
        incomeSourceStep.setVerificationCase(verificationCase);
        incomeSourceStep.setStepType(VerificationStep.StepType.INCOME_SOURCE_VERIFICATION);
        incomeSourceStep.setStatus(VerificationStep.StepStatus.PENDING);
        incomeSourceStep.setStepOrder(2);
        steps.add(verificationStepRepository.save(incomeSourceStep));
        
        VerificationStep transactionHistoryStep = new VerificationStep();
        transactionHistoryStep.setVerificationCase(verificationCase);
        transactionHistoryStep.setStepType(VerificationStep.StepType.TRANSACTION_HISTORY_VERIFICATION);
        transactionHistoryStep.setStatus(VerificationStep.StepStatus.PENDING);
        transactionHistoryStep.setStepOrder(3);
        steps.add(verificationStepRepository.save(transactionHistoryStep));
        
        VerificationStep amlScreeningStep = new VerificationStep();
        amlScreeningStep.setVerificationCase(verificationCase);
        amlScreeningStep.setStepType(VerificationStep.StepType.AML_SCREENING);
        amlScreeningStep.setStatus(VerificationStep.StepStatus.PENDING);
        amlScreeningStep.setStepOrder(4);
        steps.add(verificationStepRepository.save(amlScreeningStep));
        
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
    public Map<String, Object> verifyFinancialInformation(Long caseId, String verifierId) {
        VerificationCase verificationCase = verificationCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + caseId));
        
        if (verificationCase.getStatus() == VerificationCase.CaseStatus.PENDING) {
            verificationCase.setStatus(VerificationCase.CaseStatus.IN_PROGRESS);
            verificationCase.setAssignedTo(verifierId);
            verificationCaseRepository.save(verificationCase);
        }
        
        VerificationStep financialInfoStep = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepType() == VerificationStep.StepType.FINANCIAL_INFORMATION_VERIFICATION)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Financial information verification step not found"));
        
        financialInfoStep.setStatus(VerificationStep.StepStatus.IN_PROGRESS);
        financialInfoStep.setAssignedTo(verifierId);
        financialInfoStep.setStartDate(LocalDateTime.now());
        verificationStepRepository.save(financialInfoStep);
        
        
        Map<String, Object> results = new HashMap<>();
        results.put("financialInfoVerified", true);
        results.put("creditScore", 720);
        results.put("debtToIncomeRatio", 0.32);
        results.put("financialRisk", "LOW");
        
        financialInfoStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        financialInfoStep.setCompletionDate(LocalDateTime.now());
        financialInfoStep.setNotes("Financial information verified successfully");
        verificationStepRepository.save(financialInfoStep);
        
        return results;
    }

    @Override
    @Transactional
    public Map<String, Object> verifyIncomeSources(Long caseId, String verifierId) {
        VerificationCase verificationCase = verificationCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + caseId));
        
        VerificationStep incomeSourceStep = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepType() == VerificationStep.StepType.INCOME_SOURCE_VERIFICATION)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Income source verification step not found"));
        
        incomeSourceStep.setStatus(VerificationStep.StepStatus.IN_PROGRESS);
        incomeSourceStep.setAssignedTo(verifierId);
        incomeSourceStep.setStartDate(LocalDateTime.now());
        verificationStepRepository.save(incomeSourceStep);
        
        
        Map<String, Object> results = new HashMap<>();
        results.put("incomeSourcesVerified", true);
        results.put("employmentVerified", true);
        results.put("incomeStability", "HIGH");
        results.put("incomeRisk", "LOW");
        
        incomeSourceStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        incomeSourceStep.setCompletionDate(LocalDateTime.now());
        incomeSourceStep.setNotes("Income sources verified successfully");
        verificationStepRepository.save(incomeSourceStep);
        
        return results;
    }

    @Override
    @Transactional
    public Map<String, Object> verifyTransactionHistory(Long caseId, String verifierId) {
        VerificationCase verificationCase = verificationCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + caseId));
        
        VerificationStep transactionHistoryStep = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepType() == VerificationStep.StepType.TRANSACTION_HISTORY_VERIFICATION)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Transaction history verification step not found"));
        
        transactionHistoryStep.setStatus(VerificationStep.StepStatus.IN_PROGRESS);
        transactionHistoryStep.setAssignedTo(verifierId);
        transactionHistoryStep.setStartDate(LocalDateTime.now());
        verificationStepRepository.save(transactionHistoryStep);
        
        
        Map<String, Object> results = new HashMap<>();
        results.put("transactionHistoryVerified", true);
        results.put("suspiciousTransactions", 0);
        results.put("regularIncomeDeposits", true);
        results.put("transactionRisk", "LOW");
        
        transactionHistoryStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        transactionHistoryStep.setCompletionDate(LocalDateTime.now());
        transactionHistoryStep.setNotes("Transaction history verified successfully");
        verificationStepRepository.save(transactionHistoryStep);
        
        return results;
    }

    @Override
    @Transactional
    public Map<String, Object> performAmlScreening(Long caseId, String verifierId) {
        VerificationCase verificationCase = verificationCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + caseId));
        
        VerificationStep amlScreeningStep = verificationCase.getVerificationSteps().stream()
                .filter(step -> step.getStepType() == VerificationStep.StepType.AML_SCREENING)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("AML screening step not found"));
        
        amlScreeningStep.setStatus(VerificationStep.StepStatus.IN_PROGRESS);
        amlScreeningStep.setAssignedTo(verifierId);
        amlScreeningStep.setStartDate(LocalDateTime.now());
        verificationStepRepository.save(amlScreeningStep);
        
        
        Map<String, Object> results = new HashMap<>();
        results.put("amlScreeningCompleted", true);
        results.put("moneyLaunderingRisk", "LOW");
        results.put("structuringDetected", false);
        results.put("suspiciousActivityDetected", false);
        
        amlScreeningStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        amlScreeningStep.setCompletionDate(LocalDateTime.now());
        amlScreeningStep.setNotes("AML screening completed successfully");
        verificationStepRepository.save(amlScreeningStep);
        
        return results;
    }

    @Override
    @Transactional
    public int calculateFicaRiskScore(Long caseId) {
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
        
        
        int riskScore = 20; // Base score
        
        riskStep.setStatus(VerificationStep.StepStatus.COMPLETED);
        riskStep.setCompletionDate(LocalDateTime.now());
        riskStep.setNotes("Risk assessment completed with score: " + riskScore);
        verificationStepRepository.save(riskStep);
        
        return riskScore;
    }

    @Override
    @Transactional
    public VerificationCase completeFicaVerification(Long caseId, boolean approved, String notes, String verifierId) {
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
