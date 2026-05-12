import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PurchaseOrder } from '../../models/purchase-order.model';
import { Supplier } from '../../models/supplier.model';
import { PurchaseOrderService } from '../../services/purchase-order.service';
import { SupplierService } from '../../services/supplier.service';

@Component({
  selector: 'app-purchase-order',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './purchase-order.component.html',
  styleUrls: ['./purchase-order.component.css']
})
export class PurchaseOrderComponent implements OnInit {
  purchaseOrders: PurchaseOrder[] = [];
  suppliers: Supplier[] = [];
  selectedOrder: PurchaseOrder | null = null;
  showForm = false;
  formData: PurchaseOrder = {
    orderNumber: '',
    orderDate: '',
    status: 'PENDING',
    supplierId: 0,
    totalAmount: 0
  };
  isEditing = false;

  constructor(
    private purchaseOrderService: PurchaseOrderService,
    private supplierService: SupplierService
  ) {}

  ngOnInit(): void {
    this.loadPurchaseOrders();
    this.loadSuppliers();
  }

  loadPurchaseOrders(): void {
    this.purchaseOrderService.getAllPurchaseOrders().subscribe({
      next: (data) => {
        this.purchaseOrders = data;
      },
      error: (err) => {
        console.error('Error loading purchase orders:', err);
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

  openForm(order?: PurchaseOrder): void {
    if (order) {
      this.isEditing = true;
      this.formData = { ...order };
    } else {
      this.isEditing = false;
      this.formData = {
        orderNumber: '',
        orderDate: '',
        status: 'PENDING',
        supplierId: 0,
        totalAmount: 0
      };
    }
    this.showForm = true;
  }

  closeForm(): void {
    this.showForm = false;
  }

  savePurchaseOrder(): void {
    if (this.isEditing && this.formData.id) {
      this.purchaseOrderService.updatePurchaseOrder(this.formData.id, this.formData).subscribe({
        next: () => {
          this.loadPurchaseOrders();
          this.closeForm();
        },
        error: (err) => {
          console.error('Error updating purchase order:', err);
        }
      });
    } else {
      this.purchaseOrderService.createPurchaseOrder(this.formData).subscribe({
        next: () => {
          this.loadPurchaseOrders();
          this.closeForm();
        },
        error: (err) => {
          console.error('Error creating purchase order:', err);
        }
      });
    }
  }

  deletePurchaseOrder(id: number): void {
    if (confirm('Are you sure you want to delete this purchase order?')) {
      this.purchaseOrderService.deletePurchaseOrder(id).subscribe({
        next: () => {
          this.loadPurchaseOrders();
        },
        error: (err) => {
          console.error('Error deleting purchase order:', err);
        }
      });
    }
  }

  selectOrder(order: PurchaseOrder): void {
    this.selectedOrder = order;
  }

  getSupplierName(id: number): string {
    const supplier = this.suppliers.find(s => s.id === id);
    return supplier ? supplier.name : 'Unknown';
  }
}
