package com.ankita.playground.kyc.service.impl;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.Document;
import com.ankita.playground.kyc.model.Document.DocumentStatus;
import com.ankita.playground.kyc.model.Document.DocumentType;
import com.ankita.playground.kyc.repository.DocumentRepository;
import com.ankita.playground.kyc.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    @Override
    public List<Document> getDocumentsByCustomer(Customer customer) {
        return documentRepository.findByCustomer(customer);
    }

    @Override
    public List<Document> getDocumentsByCustomerAndType(Customer customer, DocumentType documentType) {
        return documentRepository.findByCustomerAndDocumentType(customer, documentType);
    }

    @Override
    public List<Document> getDocumentsByCustomerAndStatus(Customer customer, DocumentStatus status) {
        return documentRepository.findByCustomerAndStatus(customer, status);
    }

    @Override
    public List<Document> getDocumentsByStatus(DocumentStatus status) {
        return documentRepository.findByStatus(status);
    }

    @Override
    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document updateDocument(Long id, Document documentDetails) {
        return documentRepository.findById(id)
                .map(existingDocument -> {
                    existingDocument.setDocumentType(documentDetails.getDocumentType());
                    existingDocument.setFileName(documentDetails.getFileName());
                    existingDocument.setFileType(documentDetails.getFileType());
                    existingDocument.setFilePath(documentDetails.getFilePath());
                    existingDocument.setFileSize(documentDetails.getFileSize());
                    existingDocument.setStatus(documentDetails.getStatus());
                    existingDocument.setVerificationNotes(documentDetails.getVerificationNotes());
                    return documentRepository.save(existingDocument);
                })
                .orElseGet(() -> {
                    documentDetails.setId(id);
                    return documentRepository.save(documentDetails);
                });
    }

    @Override
    public Document updateDocumentStatus(Long id, DocumentStatus status, String verifiedBy, String notes) {
        return documentRepository.findById(id)
                .map(existingDocument -> {
                    existingDocument.setStatus(status);
                    existingDocument.setVerifiedBy(verifiedBy);
                    existingDocument.setVerificationNotes(notes);
                    existingDocument.setVerificationDate(LocalDateTime.now());
                    return documentRepository.save(existingDocument);
                })
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
