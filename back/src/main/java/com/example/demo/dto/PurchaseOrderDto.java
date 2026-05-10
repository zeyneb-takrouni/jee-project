package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseOrderDto {
    private Long id;

    @NotBlank(message = "Order number is required")
    private String orderNumber;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    private Double totalAmount;
}