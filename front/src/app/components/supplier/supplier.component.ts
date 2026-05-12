import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Supplier } from '../../models/supplier.model';
import { SupplierService } from '../../services/supplier.service';

@Component({
  selector: 'app-supplier',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './supplier.component.html',
  styleUrls: ['./supplier.component.css']
})
export class SupplierComponent implements OnInit {
  suppliers: Supplier[] = [];
  selectedSupplier: Supplier | null = null;
  showForm = false;
  formData: Supplier = {
    name: '',
    contactEmail: '',
    phoneNumber: '',
    address: '',
    qualiteService: 0,
    note: 0
  };
  isEditing = false;

  constructor(private supplierService: SupplierService) {}

  ngOnInit(): void {
    this.loadSuppliers();
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

  openForm(supplier?: Supplier): void {
    if (supplier) {
      this.isEditing = true;
      this.formData = { ...supplier };
    } else {
      this.isEditing = false;
      this.formData = {
        name: '',
        contactEmail: '',
        phoneNumber: '',
        address: '',
        qualiteService: 0,
        note: 0
      };
    }
    this.showForm = true;
  }

  closeForm(): void {
    this.showForm = false;
  }

  saveSupplier(): void {
    if (this.isEditing && this.formData.id) {
      this.supplierService.updateSupplier(this.formData.id, this.formData).subscribe({
        next: () => {
          this.loadSuppliers();
          this.closeForm();
        },
        error: (err) => {
          console.error('Error updating supplier:', err);
        }
      });
    } else {
      this.supplierService.createSupplier(this.formData).subscribe({
        next: () => {
          this.loadSuppliers();
          this.closeForm();
        },
        error: (err) => {
          console.error('Error creating supplier:', err);
        }
      });
    }
  }

  deleteSupplier(id: number): void {
    if (confirm('Are you sure you want to delete this supplier?')) {
      this.supplierService.deleteSupplier(id).subscribe({
        next: () => {
          this.loadSuppliers();
        },
        error: (err) => {
          console.error('Error deleting supplier:', err);
        }
      });
    }
  }

  selectSupplier(supplier: Supplier): void {
    this.selectedSupplier = supplier;
  }
}
