package com.ankita.playground.kyc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{4}$", message = "SSN must be in format XXX-XX-XXXX")
    private String ssn;

    private LocalDate dateOfBirth;

    @NotBlank(message = "Address is required")
    private String address;

    private String city;

    private String state;

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Zip code must be in format XXXXX or XXXXX-XXXX")
    private String zipCode;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    private String nationality;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum CustomerStatus {
        PENDING_VERIFICATION,
        VERIFIED,
        REJECTED,
        UNDER_REVIEW
    }
}
