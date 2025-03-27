package com.ankita.playground.kyc.repository;

import com.ankita.playground.kyc.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findBySsn(String ssn);
    boolean existsByEmail(String email);
    boolean existsBySsn(String ssn);
}
