package com.example.demo.controller;

import com.example.demo.dto.PurchaseHistoryDto;
import com.example.demo.entity.Supplier;
import com.example.demo.service.PurchaseHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-histories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    @GetMapping
    public ResponseEntity<List<PurchaseHistoryDto>> getAllPurchaseHistories() {
        return ResponseEntity.ok(purchaseHistoryService.getAllPurchaseHistories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseHistoryDto> getPurchaseHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseHistoryService.getPurchaseHistoryById(id));
    }

    @PostMapping
    public ResponseEntity<PurchaseHistoryDto> createPurchaseHistory(@Valid @RequestBody PurchaseHistoryDto purchaseHistoryDto) {
        return new ResponseEntity<>(purchaseHistoryService.createPurchaseHistory(purchaseHistoryDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseHistoryDto> updatePurchaseHistory(@PathVariable Long id, @Valid @RequestBody PurchaseHistoryDto purchaseHistoryDto) {
        return ResponseEntity.ok(purchaseHistoryService.updatePurchaseHistory(id, purchaseHistoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseHistory(@PathVariable Long id) {
        purchaseHistoryService.deletePurchaseHistory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/evaluate-supplier/{supplierId}")
    public ResponseEntity<Double> evaluateSupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(purchaseHistoryService.evaluateSupplier(supplierId));
    }

    @GetMapping("/compare-offers/{productId}")
    public ResponseEntity<List<Supplier>> compareOffers(@PathVariable Long productId) {
        return ResponseEntity.ok(purchaseHistoryService.compareOffers(productId));
    }
}