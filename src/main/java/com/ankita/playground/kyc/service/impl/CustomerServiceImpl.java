package com.ankita.playground.kyc.service.impl;

import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.repository.CustomerRepository;
import com.ankita.playground.kyc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Optional<Customer> getCustomerBySsn(String ssn) {
        return customerRepository.findBySsn(ssn);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setFirstName(customerDetails.getFirstName());
                    existingCustomer.setLastName(customerDetails.getLastName());
                    existingCustomer.setEmail(customerDetails.getEmail());
                    existingCustomer.setAddress(customerDetails.getAddress());
                    existingCustomer.setCity(customerDetails.getCity());
                    existingCustomer.setState(customerDetails.getState());
                    existingCustomer.setZipCode(customerDetails.getZipCode());
                    existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());
                    existingCustomer.setNationality(customerDetails.getNationality());
                    existingCustomer.setStatus(customerDetails.getStatus());
                    return customerRepository.save(existingCustomer);
                })
                .orElseGet(() -> {
                    customerDetails.setId(id);
                    return customerRepository.save(customerDetails);
                });
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsBySsn(String ssn) {
        return customerRepository.existsBySsn(ssn);
    }
}
