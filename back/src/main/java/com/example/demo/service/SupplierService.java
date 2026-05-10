package com.example.demo.service;

import com.example.demo.dto.SupplierDto;
import com.example.demo.entity.Supplier;
import com.example.demo.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SupplierDto createSupplier(SupplierDto supplierDto) {
        Supplier supplier = mapToEntity(supplierDto);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return mapToDto(savedSupplier);
    }

    private SupplierDto mapToDto(Supplier supplier) {
        SupplierDto dto = new SupplierDto();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setContactEmail(supplier.getContactEmail());
        dto.setPhoneNumber(supplier.getPhoneNumber());
        dto.setAddress(supplier.getAddress());
        return dto;
    }

    private Supplier mapToEntity(SupplierDto dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setContactEmail(dto.getContactEmail());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setAddress(dto.getAddress());
        return supplier;
    }
}
