package com.example.demo.controller;

import com.example.demo.dto.PurchaseOrderLineDto;
import com.example.demo.service.PurchaseOrderLineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-order-lines")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PurchaseOrderLineController {

    private final PurchaseOrderLineService purchaseOrderLineService;

    @GetMapping
    public ResponseEntity<List<PurchaseOrderLineDto>> getAllPurchaseOrderLines() {
        return ResponseEntity.ok(purchaseOrderLineService.getAllPurchaseOrderLines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderLineDto> getPurchaseOrderLineById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrderLineService.getPurchaseOrderLineById(id));
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderLineDto> createPurchaseOrderLine(@Valid @RequestBody PurchaseOrderLineDto purchaseOrderLineDto) {
        return new ResponseEntity<>(purchaseOrderLineService.createPurchaseOrderLine(purchaseOrderLineDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderLineDto> updatePurchaseOrderLine(@PathVariable Long id, @Valid @RequestBody PurchaseOrderLineDto purchaseOrderLineDto) {
        return ResponseEntity.ok(purchaseOrderLineService.updatePurchaseOrderLine(id, purchaseOrderLineDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrderLine(@PathVariable Long id) {
        purchaseOrderLineService.deletePurchaseOrderLine(id);
        return ResponseEntity.noContent().build();
    }
}