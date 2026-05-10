package com.example.demo.controller;

import com.example.demo.dto.SupplierDto;
import com.example.demo.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow frontend to access
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @PostMapping
    public ResponseEntity<SupplierDto> createSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        return new ResponseEntity<>(supplierService.createSupplier(supplierDto), HttpStatus.CREATED);
    }
}
