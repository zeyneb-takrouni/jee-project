package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseHistoryDto {
    private Long id;

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    private Integer quantite;

    @NotNull(message = "Delivery delay is required")
    private Integer delaiLivraison;
}