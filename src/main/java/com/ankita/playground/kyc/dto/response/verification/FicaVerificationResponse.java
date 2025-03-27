package com.ankita.playground.kyc.dto.response.verification;

import com.ankita.playground.kyc.model.VerificationCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FicaVerificationResponse {
    private Long caseId;
    private String caseReference;
    private String status;
    private String customerName;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private List<VerificationStepDto> steps;
    private Map<String, Object> verificationResults;
    private Integer riskScore;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationStepDto {
        private Long stepId;
        private String stepType;
        private String status;
        private Integer stepOrder;
        private String assignedTo;
        private LocalDateTime startDate;
        private LocalDateTime completionDate;
        private String notes;
    }
    
    public static FicaVerificationResponse fromVerificationCase(VerificationCase verificationCase) {
        FicaVerificationResponse response = new FicaVerificationResponse();
        response.setCaseId(verificationCase.getId());
        response.setCaseReference(verificationCase.getCaseReference());
        response.setStatus(verificationCase.getStatus().name());
        response.setCustomerName(verificationCase.getCustomer().getFullName());
        response.setCreatedAt(verificationCase.getCreatedAt());
        response.setCompletedAt(verificationCase.getCompletedAt());
        
        List<VerificationStepDto> stepDtos = verificationCase.getVerificationSteps().stream()
                .map(step -> {
                    VerificationStepDto dto = new VerificationStepDto();
                    dto.setStepId(step.getId());
                    dto.setStepType(step.getStepType().name());
                    dto.setStatus(step.getStatus().name());
                    dto.setStepOrder(step.getStepOrder());
                    dto.setAssignedTo(step.getAssignedTo());
                    dto.setStartDate(step.getStartDate());
                    dto.setCompletionDate(step.getCompletionDate());
                    dto.setNotes(step.getNotes());
                    return dto;
                })
                .sorted((s1, s2) -> s1.getStepOrder().compareTo(s2.getStepOrder()))
                .toList();
        
        response.setSteps(stepDtos);
        
        return response;
    }
}
