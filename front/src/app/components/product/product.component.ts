import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Product } from '../../models/product.model';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  products: Product[] = [];
  selectedProduct: Product | null = null;
  showForm = false;
  formData: Product = {
    name: '',
    description: '',
    category: ''
  };
  isEditing = false;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
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

  openForm(product?: Product): void {
    if (product) {
      this.isEditing = true;
      this.formData = { ...product };
    } else {
      this.isEditing = false;
      this.formData = {
        name: '',
        description: '',
        category: ''
      };
    }
    this.showForm = true;
  }

  closeForm(): void {
    this.showForm = false;
  }

  saveProduct(): void {
    if (this.isEditing && this.formData.id) {
      this.productService.updateProduct(this.formData.id, this.formData).subscribe({
        next: () => {
          this.loadProducts();
          this.closeForm();
        },
        error: (err) => {
          console.error('Error updating product:', err);
        }
      });
    } else {
      this.productService.createProduct(this.formData).subscribe({
        next: () => {
          this.loadProducts();
          this.closeForm();
        },
        error: (err) => {
          console.error('Error creating product:', err);
        }
      });
    }
  }

  deleteProduct(id: number): void {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(id).subscribe({
        next: () => {
          this.loadProducts();
        },
        error: (err) => {
          console.error('Error deleting product:', err);
        }
      });
    }
  }

  selectProduct(product: Product): void {
    this.selectedProduct = product;
  }
}
