package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierDto {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String contactEmail;

    private String phoneNumber;
    
    private String address;

    @NotNull(message = "Service quality is required")
    private Double qualiteService;

    @NotNull(message = "Note is required")
    private Double note;
