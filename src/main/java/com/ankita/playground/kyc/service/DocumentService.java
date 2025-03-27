package com.ankita.playground.kyc.service;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.Document;
import com.ankita.playground.kyc.model.Document.DocumentStatus;
import com.ankita.playground.kyc.model.Document.DocumentType;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    List<Document> getAllDocuments();
    Optional<Document> getDocumentById(Long id);
    List<Document> getDocumentsByCustomer(Customer customer);
    List<Document> getDocumentsByCustomerAndType(Customer customer, DocumentType documentType);
    List<Document> getDocumentsByCustomerAndStatus(Customer customer, DocumentStatus status);
    List<Document> getDocumentsByStatus(DocumentStatus status);
    Document createDocument(Document document);
    Document updateDocument(Long id, Document documentDetails);
    Document updateDocumentStatus(Long id, DocumentStatus status, String verifiedBy, String notes);
    void deleteDocument(Long id);
}
