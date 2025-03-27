package com.ankita.playground.kyc.repository;

import com.ankita.playground.kyc.model.VerificationCase;
import com.ankita.playground.kyc.model.VerificationStep;
import com.ankita.playground.kyc.model.VerificationStep.StepStatus;
import com.ankita.playground.kyc.model.VerificationStep.StepType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationStepRepository extends JpaRepository<VerificationStep, Long> {
    List<VerificationStep> findByVerificationCase(VerificationCase verificationCase);
    List<VerificationStep> findByVerificationCaseAndStepType(VerificationCase verificationCase, StepType stepType);
    List<VerificationStep> findByVerificationCaseAndStatus(VerificationCase verificationCase, StepStatus status);
    List<VerificationStep> findByAssignedTo(String assignedTo);
}
