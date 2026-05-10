package com.example.demo.service;

import com.example.demo.dto.PurchaseOrderLineDto;
import com.example.demo.entity.Product;
import com.example.demo.entity.PurchaseOrder;
import com.example.demo.entity.PurchaseOrderLine;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseOrderLineRepository;
import com.example.demo.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderLineService {

    private final PurchaseOrderLineRepository purchaseOrderLineRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;

    public List<PurchaseOrderLineDto> getAllPurchaseOrderLines() {
        return purchaseOrderLineRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PurchaseOrderLineDto getPurchaseOrderLineById(Long id) {
        PurchaseOrderLine purchaseOrderLine = purchaseOrderLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order line not found"));
        return mapToDto(purchaseOrderLine);
    }

    public PurchaseOrderLineDto createPurchaseOrderLine(PurchaseOrderLineDto purchaseOrderLineDto) {
        PurchaseOrderLine purchaseOrderLine = mapToEntity(purchaseOrderLineDto);
        PurchaseOrderLine savedPurchaseOrderLine = purchaseOrderLineRepository.save(purchaseOrderLine);
        return mapToDto(savedPurchaseOrderLine);
    }

    public PurchaseOrderLineDto updatePurchaseOrderLine(Long id, PurchaseOrderLineDto purchaseOrderLineDto) {
        PurchaseOrderLine purchaseOrderLine = purchaseOrderLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order line not found"));
        if (purchaseOrderLineDto.getPurchaseOrderId() != null) {
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderLineDto.getPurchaseOrderId())
                    .orElseThrow(() -> new RuntimeException("Purchase order not found"));
            purchaseOrderLine.setPurchaseOrder(purchaseOrder);
        }
        if (purchaseOrderLineDto.getProductId() != null) {
            Product product = productRepository.findById(purchaseOrderLineDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            purchaseOrderLine.setProduct(product);
        }
        purchaseOrderLine.setQuantite(purchaseOrderLineDto.getQuantite());
        purchaseOrderLine.setPrixUnitaire(purchaseOrderLineDto.getPrixUnitaire());
        PurchaseOrderLine updatedPurchaseOrderLine = purchaseOrderLineRepository.save(purchaseOrderLine);
        return mapToDto(updatedPurchaseOrderLine);
    }

    public void deletePurchaseOrderLine(Long id) {
        purchaseOrderLineRepository.deleteById(id);
    }

    private PurchaseOrderLineDto mapToDto(PurchaseOrderLine purchaseOrderLine) {
        PurchaseOrderLineDto dto = new PurchaseOrderLineDto();
        dto.setId(purchaseOrderLine.getId());
        dto.setPurchaseOrderId(purchaseOrderLine.getPurchaseOrder().getId());
        dto.setProductId(purchaseOrderLine.getProduct().getId());
        dto.setQuantite(purchaseOrderLine.getQuantite());
        dto.setPrixUnitaire(purchaseOrderLine.getPrixUnitaire());
        return dto;
    }

    private PurchaseOrderLine mapToEntity(PurchaseOrderLineDto dto) {
        PurchaseOrderLine purchaseOrderLine = new PurchaseOrderLine();
        if (dto.getPurchaseOrderId() != null) {
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(dto.getPurchaseOrderId())
                    .orElseThrow(() -> new RuntimeException("Purchase order not found"));
            purchaseOrderLine.setPurchaseOrder(purchaseOrder);
        }
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            purchaseOrderLine.setProduct(product);
        }
        purchaseOrderLine.setQuantite(dto.getQuantite());
        purchaseOrderLine.setPrixUnitaire(dto.getPrixUnitaire());
        return purchaseOrderLine;
    }
}