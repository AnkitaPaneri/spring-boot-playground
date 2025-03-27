package com.ankita.playground.kyc.service;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.VerificationCase;
import com.ankita.playground.kyc.model.VerificationCase.CaseStatus;
import com.ankita.playground.kyc.model.VerificationCase.CaseType;

import java.util.List;
import java.util.Optional;

public interface VerificationCaseService {
    List<VerificationCase> getAllCases();
    Optional<VerificationCase> getCaseById(Long id);
    Optional<VerificationCase> getCaseByCaseNumber(String caseNumber);
    List<VerificationCase> getCasesByCustomer(Customer customer);
    List<VerificationCase> getCasesByStatus(CaseStatus status);
    List<VerificationCase> getCasesByType(CaseType caseType);
    List<VerificationCase> getCasesByAssignedTo(String assignedTo);
    VerificationCase createCase(VerificationCase verificationCase);
    VerificationCase updateCase(Long id, VerificationCase caseDetails);
    VerificationCase updateCaseStatus(Long id, CaseStatus status);
    VerificationCase assignCase(Long id, String assignedTo);
    void deleteCase(Long id);
}
