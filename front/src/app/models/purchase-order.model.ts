export interface PurchaseOrder {
  id?: number;
  orderNumber: string;
  orderDate: string;
  status: string;
  supplierId: number;
  totalAmount?: number;
}
