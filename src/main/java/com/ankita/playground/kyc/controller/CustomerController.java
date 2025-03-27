package com.ankita.playground.kyc.controller;

import com.ankita.playground.kyc.dto.request.CustomerRequest;
import com.ankita.playground.kyc.dto.response.CustomerResponse;
import com.ankita.playground.kyc.model.Customer;
import com.ankita.playground.kyc.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Value("${api.prefix}")
    private String apiPrefix;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(customer -> ResponseEntity.ok(convertToResponse(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        if (customerService.existsByEmail(customerRequest.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        if (customerService.existsBySsn(customerRequest.getSsn())) {
            return ResponseEntity.badRequest().build();
        }

        Customer customer = convertToEntity(customerRequest);
        customer.setStatus(Customer.CustomerStatus.PENDING_VERIFICATION);
        Customer savedCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(convertToResponse(savedCustomer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest customerRequest) {
        if (!customerService.getCustomerById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = convertToEntity(customerRequest);
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(convertToResponse(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (!customerService.getCustomerById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    private Customer convertToEntity(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setSsn(customerRequest.getSsn());
        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setAddress(customerRequest.getAddress());
        customer.setCity(customerRequest.getCity());
        customer.setState(customerRequest.getState());
        customer.setZipCode(customerRequest.getZipCode());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setNationality(customerRequest.getNationality());
        return customer;
    }

    private CustomerResponse convertToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setSsn(customer.getSsn());
        response.setDateOfBirth(customer.getDateOfBirth());
        response.setAddress(customer.getAddress());
        response.setCity(customer.getCity());
        response.setState(customer.getState());
        response.setZipCode(customer.getZipCode());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setNationality(customer.getNationality());
        response.setStatus(customer.getStatus());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());
        return response;
    }
}
