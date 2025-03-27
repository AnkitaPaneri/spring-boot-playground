package com.ankita.playground.kyc.service.verification;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.Document;
import com.ankita.playground.kyc.model.VerificationCase;

import java.util.List;
import java.util.Map;

public interface FicaVerificationService {
    /**
     * Initiates a new FICA verification process for a customer
     * @param customer The customer to verify
     * @param documents Optional list of initial documents for verification
     * @return The created verification case
     */
    VerificationCase initiateFicaVerification(Customer customer, List<Document> documents);
    
    /**
     * Verifies customer financial information against provided documents and external sources
     * @param caseId The verification case ID
     * @param verifierId ID of the user performing the verification
     * @return Map containing verification results
     */
    Map<String, Object> verifyFinancialInformation(Long caseId, String verifierId);
    
    /**
     * Verifies customer income sources against provided documents and external sources
     * @param caseId The verification case ID
     * @param verifierId ID of the user performing the verification
     * @return Map containing verification results
     */
    Map<String, Object> verifyIncomeSources(Long caseId, String verifierId);
    
    /**
     * Verifies customer transaction history against provided documents and external sources
     * @param caseId The verification case ID
     * @param verifierId ID of the user performing the verification
     * @return Map containing verification results
     */
    Map<String, Object> verifyTransactionHistory(Long caseId, String verifierId);
    
    /**
     * Performs AML (Anti-Money Laundering) screening for the customer
     * @param caseId The verification case ID
     * @param verifierId ID of the user performing the verification
     * @return Map containing screening results
     */
    Map<String, Object> performAmlScreening(Long caseId, String verifierId);
    
    /**
     * Calculates the overall FICA risk score for a customer
     * @param caseId The verification case ID
     * @return Risk score (0-100, higher means higher risk)
     */
    int calculateFicaRiskScore(Long caseId);
    
    /**
     * Completes the FICA verification process with a final decision
     * @param caseId The verification case ID
     * @param approved Whether the verification is approved
     * @param notes Additional notes for the decision
     * @param verifierId ID of the user completing the verification
     * @return The updated verification case
     */
    VerificationCase completeFicaVerification(Long caseId, boolean approved, String notes, String verifierId);
}
