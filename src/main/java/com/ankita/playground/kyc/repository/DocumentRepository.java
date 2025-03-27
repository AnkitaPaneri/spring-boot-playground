package com.ankita.playground.kyc.repository;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.model.Document;
import com.ankita.playground.kyc.model.Document.DocumentStatus;
import com.ankita.playground.kyc.model.Document.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCustomer(Customer customer);
    List<Document> findByCustomerAndDocumentType(Customer customer, DocumentType documentType);
    List<Document> findByCustomerAndStatus(Customer customer, DocumentStatus status);
    List<Document> findByStatus(DocumentStatus status);
}
