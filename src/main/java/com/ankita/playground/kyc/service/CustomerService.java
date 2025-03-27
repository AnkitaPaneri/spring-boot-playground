package com.ankita.playground.kyc.service;

import com.ankita.playground.kyc.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long id);
    Optional<Customer> getCustomerByEmail(String email);
    Optional<Customer> getCustomerBySsn(String ssn);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customerDetails);
    void deleteCustomer(Long id);
    boolean existsByEmail(String email);
    boolean existsBySsn(String ssn);
}
