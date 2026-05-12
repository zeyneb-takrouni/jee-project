import { Routes } from '@angular/router';
import { SupplierComponent } from './components/supplier/supplier.component';
import { ProductComponent } from './components/product/product.component';
import { PurchaseOrderComponent } from './components/purchase-order/purchase-order.component';
import { PurchaseHistoryComponent } from './components/purchase-history/purchase-history.component';

export const routes: Routes = [
  { path: '', redirectTo: '/suppliers', pathMatch: 'full' },
  { path: 'suppliers', component: SupplierComponent },
  { path: 'products', component: ProductComponent },
  { path: 'purchase-orders', component: PurchaseOrderComponent },
  { path: 'purchase-history', component: PurchaseHistoryComponent }
];
