package com.ankita.playground.kyc.service.impl;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.VerificationCase;
import com.ankita.playground.kyc.model.VerificationCase.CaseStatus;
import com.ankita.playground.kyc.model.VerificationCase.CaseType;
import com.ankita.playground.kyc.repository.VerificationCaseRepository;
import com.ankita.playground.kyc.service.VerificationCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VerificationCaseServiceImpl implements VerificationCaseService {

    private final VerificationCaseRepository verificationCaseRepository;

    @Autowired
    public VerificationCaseServiceImpl(VerificationCaseRepository verificationCaseRepository) {
        this.verificationCaseRepository = verificationCaseRepository;
    }

    @Override
    public List<VerificationCase> getAllCases() {
        return verificationCaseRepository.findAll();
    }

    @Override
    public Optional<VerificationCase> getCaseById(Long id) {
        return verificationCaseRepository.findById(id);
    }

    @Override
    public Optional<VerificationCase> getCaseByCaseNumber(String caseNumber) {
        return verificationCaseRepository.findByCaseNumber(caseNumber);
    }

    @Override
    public List<VerificationCase> getCasesByCustomer(Customer customer) {
        return verificationCaseRepository.findByCustomer(customer);
    }

    @Override
    public List<VerificationCase> getCasesByStatus(CaseStatus status) {
        return verificationCaseRepository.findByStatus(status);
    }

    @Override
    public List<VerificationCase> getCasesByType(CaseType caseType) {
        return verificationCaseRepository.findByCaseType(caseType);
    }

    @Override
    public List<VerificationCase> getCasesByAssignedTo(String assignedTo) {
        return verificationCaseRepository.findByAssignedTo(assignedTo);
    }

    @Override
    public VerificationCase createCase(VerificationCase verificationCase) {
        return verificationCaseRepository.save(verificationCase);
    }

    @Override
    public VerificationCase updateCase(Long id, VerificationCase caseDetails) {
        return verificationCaseRepository.findById(id)
                .map(existingCase -> {
                    existingCase.setCaseType(caseDetails.getCaseType());
                    existingCase.setStatus(caseDetails.getStatus());
                    existingCase.setPriority(caseDetails.getPriority());
                    existingCase.setAssignedTo(caseDetails.getAssignedTo());
                    existingCase.setVerificationSteps(caseDetails.getVerificationSteps());
                    return verificationCaseRepository.save(existingCase);
                })
                .orElseGet(() -> {
                    caseDetails.setId(id);
                    return verificationCaseRepository.save(caseDetails);
                });
    }

    @Override
    public VerificationCase updateCaseStatus(Long id, CaseStatus status) {
        return verificationCaseRepository.findById(id)
                .map(existingCase -> {
                    existingCase.setStatus(status);
                    if (status == CaseStatus.COMPLETED) {
                        existingCase.setCompletedAt(LocalDateTime.now());
                    }
                    return verificationCaseRepository.save(existingCase);
                })
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + id));
    }

    @Override
    public VerificationCase assignCase(Long id, String assignedTo) {
        return verificationCaseRepository.findById(id)
                .map(existingCase -> {
                    existingCase.setAssignedTo(assignedTo);
                    if (existingCase.getStatus() == CaseStatus.PENDING) {
                        existingCase.setStatus(CaseStatus.IN_PROGRESS);
                    }
                    return verificationCaseRepository.save(existingCase);
                })
                .orElseThrow(() -> new RuntimeException("Verification case not found with id: " + id));
    }

    @Override
    public void deleteCase(Long id) {
        verificationCaseRepository.deleteById(id);
    }
}
