import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PurchaseHistory } from '../../models/purchase-history.model';
import { Supplier } from '../../models/supplier.model';
import { Product } from '../../models/product.model';
import { PurchaseHistoryService } from '../../services/purchase-history.service';
import { SupplierService } from '../../services/supplier.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-purchase-history',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './purchase-history.component.html',
  styleUrls: ['./purchase-history.component.css']
})
export class PurchaseHistoryComponent implements OnInit {
  histories: PurchaseHistory[] = [];
  suppliers: Supplier[] = [];
  products: Product[] = [];
  selectedHistory: PurchaseHistory | null = null;
  showForm = false;
  showEvaluation = false;
  showComparison = false;
  formData: PurchaseHistory = {
    supplierId: 0,
    productId: 0,
    quantite: 0,
    delaiLivraison: 0
  };
  isEditing = false;
  evaluationResult: number | null = null;
  comparisonResults: Supplier[] = [];
  selectedSupplierId: number = 0;
  selectedProductId: number = 0;

  constructor(
    private purchaseHistoryService: PurchaseHistoryService,
    private supplierService: SupplierService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.loadHistories();
    this.loadSuppliers();
    this.loadProducts();
  }

  loadHistories(): void {
    this.purchaseHistoryService.getAllPurchaseHistories().subscribe({
      next: (data) => {
        this.histories = data;
      },
      error: (err) => {
        console.error('Error loading purchase histories:', err);
      }
    });
  }

  loadSuppliers(): void {
    this.supplierService.getAllSuppliers().subscribe({
      next: (data) => {
        this.suppliers = data;
      },
      error: (err) => {
        console.error('Error loading suppliers:', err);
      }
    });
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (err) => {
        console.error('Error loading products:', err);
      }
    });
  }

  openForm(history?: PurchaseHistory): void {
    if (history) {
      this.isEditing = true;
      this.formData = { ...history };
    } else {
      this.isEditing = false;
      this.formData = {
        supplierId: 0,
        productId: 0,
        quantite: 0,
        delaiLivraison: 0
      };
    }
    this.showForm = true;
  }

  closeForm(): void {
    this.showForm = false;
  }

  savePurchaseHistory(): void {
    if (this.isEditing && this.formData.id) {
      this.purchaseHistoryService.updatePurchaseHistory(this.formData.id, this.formData).subscribe({
        next: () => {
          this.loadHistories();
          this.closeForm();
        },
        error: (err) => {
          console.error('Error updating purchase history:', err);
        }
      });
    } else {
      this.purchaseHistoryService.createPurchaseHistory(this.formData).subscribe({
        next: () => {
          this.loadHistories();
          this.closeForm();
        },
        error: (err) => {
          console.error('Error creating purchase history:', err);
        }
      });
    }
  }

  deletePurchaseHistory(id: number): void {
    if (confirm('Are you sure you want to delete this record?')) {
      this.purchaseHistoryService.deletePurchaseHistory(id).subscribe({
        next: () => {
          this.loadHistories();
        },
        error: (err) => {
          console.error('Error deleting purchase history:', err);
        }
      });
    }
  }

  selectHistory(history: PurchaseHistory): void {
    this.selectedHistory = history;
  }

  evaluateSupplier(): void {
    if (this.selectedSupplierId) {
      this.purchaseHistoryService.evaluateSupplier(this.selectedSupplierId).subscribe({
        next: (result) => {
          this.evaluationResult = result;
          this.showEvaluation = true;
        },
        error: (err) => {
          console.error('Error evaluating supplier:', err);
        }
      });
    }
  }

  compareOffers(): void {
    if (this.selectedProductId) {
      this.purchaseHistoryService.compareOffers(this.selectedProductId).subscribe({
        next: (result) => {
          this.comparisonResults = result;
          this.showComparison = true;
        },
        error: (err) => {
          console.error('Error comparing offers:', err);
        }
      });
    }
  }

  getSupplierName(id: number): string {
    const supplier = this.suppliers.find(s => s.id === id);
    return supplier ? supplier.name : 'Unknown';
  }

  getProductName(id: number): string {
    const product = this.products.find(p => p.id === id);
    return product ? product.name : 'Unknown';
  }

  closeEvaluation(): void {
    this.showEvaluation = false;
    this.evaluationResult = null;
  }

  closeComparison(): void {
    this.showComparison = false;
    this.comparisonResults = [];
  }
}
