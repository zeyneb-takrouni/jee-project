package com.example.demo.service;

import com.example.demo.dto.PurchaseHistoryDto;
import com.example.demo.dto.SupplierDto;
import com.example.demo.entity.Product;
import com.example.demo.entity.PurchaseHistory;
import com.example.demo.entity.Supplier;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseHistoryRepository;
import com.example.demo.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public List<PurchaseHistoryDto> getAllPurchaseHistories() {
        return purchaseHistoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PurchaseHistoryDto getPurchaseHistoryById(Long id) {
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase history not found"));
        return mapToDto(purchaseHistory);
    }

    public PurchaseHistoryDto createPurchaseHistory(PurchaseHistoryDto purchaseHistoryDto) {
        PurchaseHistory purchaseHistory = mapToEntity(purchaseHistoryDto);
        PurchaseHistory savedPurchaseHistory = purchaseHistoryRepository.save(purchaseHistory);
        return mapToDto(savedPurchaseHistory);
    }

    public PurchaseHistoryDto updatePurchaseHistory(Long id, PurchaseHistoryDto purchaseHistoryDto) {
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase history not found"));
        if (purchaseHistoryDto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(purchaseHistoryDto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            purchaseHistory.setSupplier(supplier);
        }
        if (purchaseHistoryDto.getProductId() != null) {
            Product product = productRepository.findById(purchaseHistoryDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            purchaseHistory.setProduct(product);
        }
        purchaseHistory.setQuantite(purchaseHistoryDto.getQuantite());
        purchaseHistory.setDelaiLivraison(purchaseHistoryDto.getDelaiLivraison());
        PurchaseHistory updatedPurchaseHistory = purchaseHistoryRepository.save(purchaseHistory);
        return mapToDto(updatedPurchaseHistory);
    }

    public void deletePurchaseHistory(Long id) {
        purchaseHistoryRepository.deleteById(id);
    }

    // Logique métier : Évaluation des fournisseurs
    public Double evaluateSupplier(Long supplierId) {
        List<PurchaseHistory> histories = purchaseHistoryRepository.findAll().stream()
                .filter(h -> h.getSupplier().getId().equals(supplierId))
                .collect(Collectors.toList());
        if (histories.isEmpty()) return 0.0;
        double avgDelay = histories.stream().mapToInt(PurchaseHistory::getDelaiLivraison).average().orElse(0.0);
        // Note basée sur délai (plus bas = mieux, max 10 jours pour note 10)
        double delayScore = Math.max(0, 10 - (avgDelay / 10) * 10);
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
        return (supplier.getQualiteService() + supplier.getNote() + delayScore) / 3;
    }

    // Comparaison des offres (simplifiée : par prix moyen)
    public List<SupplierDto> compareOffers(Long productId) {
        // Retourner les fournisseurs triés par note décroissante pour ce produit
        return purchaseHistoryRepository.findAll().stream()
                .filter(h -> h.getProduct().getId().equals(productId))
                .map(PurchaseHistory::getSupplier)
                .distinct()
                .sorted((s1, s2) -> Double.compare(evaluateSupplier(s2.getId()), evaluateSupplier(s1.getId())))
                .map(this::mapToSupplierDto)
                .collect(Collectors.toList());
    }

    private SupplierDto mapToSupplierDto(Supplier supplier) {
        SupplierDto dto = new SupplierDto();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setContactEmail(supplier.getContactEmail());
        dto.setPhoneNumber(supplier.getPhoneNumber());
        dto.setAddress(supplier.getAddress());
        dto.setQualiteService(supplier.getQualiteService());
        dto.setNote(supplier.getNote());
        return dto;
    }

    private PurchaseHistoryDto mapToDto(PurchaseHistory purchaseHistory) {
        PurchaseHistoryDto dto = new PurchaseHistoryDto();
        dto.setId(purchaseHistory.getId());
        dto.setSupplierId(purchaseHistory.getSupplier().getId());
        dto.setProductId(purchaseHistory.getProduct().getId());
        dto.setQuantite(purchaseHistory.getQuantite());
        dto.setDelaiLivraison(purchaseHistory.getDelaiLivraison());
        return dto;
    }

    private PurchaseHistory mapToEntity(PurchaseHistoryDto dto) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            purchaseHistory.setSupplier(supplier);
        }
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            purchaseHistory.setProduct(product);
        }
        purchaseHistory.setQuantite(dto.getQuantite());
        purchaseHistory.setDelaiLivraison(dto.getDelaiLivraison());
        return purchaseHistory;
    }
}