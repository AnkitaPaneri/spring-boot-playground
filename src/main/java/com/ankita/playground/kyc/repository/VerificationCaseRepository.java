package com.ankita.playground.kyc.repository;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.VerificationCase;
import com.ankita.playground.kyc.model.VerificationCase.CaseStatus;
import com.ankita.playground.kyc.model.VerificationCase.CaseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationCaseRepository extends JpaRepository<VerificationCase, Long> {
    List<VerificationCase> findByCustomer(Customer customer);
    List<VerificationCase> findByStatus(CaseStatus status);
    List<VerificationCase> findByCaseType(CaseType caseType);
    List<VerificationCase> findByAssignedTo(String assignedTo);
    Optional<VerificationCase> findByCaseNumber(String caseNumber);
}
