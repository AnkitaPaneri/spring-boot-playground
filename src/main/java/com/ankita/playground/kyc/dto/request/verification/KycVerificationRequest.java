package com.ankita.playground.kyc.dto.request.verification;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KycVerificationRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    private List<Long> documentIds;
    
    private String notes;
}
