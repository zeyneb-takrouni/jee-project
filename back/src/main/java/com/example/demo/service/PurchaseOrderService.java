package com.example.demo.service;

import com.example.demo.dto.PurchaseOrderDto;
import com.example.demo.entity.PurchaseOrder;
import com.example.demo.entity.Supplier;
import com.example.demo.repository.PurchaseOrderRepository;
import com.example.demo.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;

    public List<PurchaseOrderDto> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PurchaseOrderDto getPurchaseOrderById(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        return mapToDto(purchaseOrder);
    }

    public PurchaseOrderDto createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = mapToEntity(purchaseOrderDto);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        return mapToDto(savedPurchaseOrder);
    }

    public PurchaseOrderDto updatePurchaseOrder(Long id, PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        purchaseOrder.setOrderNumber(purchaseOrderDto.getOrderNumber());
        purchaseOrder.setOrderDate(purchaseOrderDto.getOrderDate());
        purchaseOrder.setStatus(purchaseOrderDto.getStatus());
        if (purchaseOrderDto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(purchaseOrderDto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            purchaseOrder.setSupplier(supplier);
        }
        purchaseOrder.setTotalAmount(purchaseOrderDto.getTotalAmount());
        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        return mapToDto(updatedPurchaseOrder);
    }

    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

    // Logique métier : Calcul du montant total basé sur les lignes (si nécessaire)
    public Double calculateTotalAmount(Long purchaseOrderId) {
        // Ici, on pourrait calculer en sommant les lignes, mais pour simplicité, on retourne le totalAmount
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        return purchaseOrder.getTotalAmount();
    }

    private PurchaseOrderDto mapToDto(PurchaseOrder purchaseOrder) {
        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setId(purchaseOrder.getId());
        dto.setOrderNumber(purchaseOrder.getOrderNumber());
        dto.setOrderDate(purchaseOrder.getOrderDate());
        dto.setStatus(purchaseOrder.getStatus());
        dto.setSupplierId(purchaseOrder.getSupplier().getId());
        dto.setTotalAmount(purchaseOrder.getTotalAmount());
        return dto;
    }

    private PurchaseOrder mapToEntity(PurchaseOrderDto dto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderNumber(dto.getOrderNumber());
        purchaseOrder.setOrderDate(dto.getOrderDate());
        purchaseOrder.setStatus(dto.getStatus());
        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            purchaseOrder.setSupplier(supplier);
        }
        purchaseOrder.setTotalAmount(dto.getTotalAmount());
        return purchaseOrder;
    }
}