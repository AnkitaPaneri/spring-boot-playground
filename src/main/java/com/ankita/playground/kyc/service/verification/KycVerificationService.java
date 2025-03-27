package com.ankita.playground.kyc.service.verification;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.Document;
import com.ankita.playground.kyc.model.VerificationCase;

import java.util.List;
import java.util.Map;

public interface KycVerificationService {
    /**
     * Initiates a new KYC verification process for a customer
     * @param customer The customer to verify
     * @param documents Optional list of initial documents for verification
     * @return The created verification case
     */
    VerificationCase initiateKycVerification(Customer customer, List<Document> documents);
    
    /**
     * Verifies customer identity information against provided documents and external sources
     * @param caseId The verification case ID
     * @param verifierId ID of the user performing the verification
     * @return Map containing verification results
     */
    Map<String, Object> verifyIdentity(Long caseId, String verifierId);
    
    /**
     * Verifies customer address information against provided documents and external sources
     * @param caseId The verification case ID
     * @param verifierId ID of the user performing the verification
     * @return Map containing verification results
     */
    Map<String, Object> verifyAddress(Long caseId, String verifierId);
    
    /**
     * Performs watchlist screening for the customer
     * @param caseId The verification case ID
     * @param verifierId ID of the user performing the verification
     * @return Map containing screening results
     */
    Map<String, Object> performWatchlistScreening(Long caseId, String verifierId);
    
    /**
     * Calculates the overall KYC risk score for a customer
     * @param caseId The verification case ID
     * @return Risk score (0-100, higher means higher risk)
     */
    int calculateKycRiskScore(Long caseId);
    
    /**
     * Completes the KYC verification process with a final decision
     * @param caseId The verification case ID
     * @param approved Whether the verification is approved
     * @param notes Additional notes for the decision
     * @param verifierId ID of the user completing the verification
     * @return The updated verification case
     */
    VerificationCase completeKycVerification(Long caseId, boolean approved, String notes, String verifierId);
}
