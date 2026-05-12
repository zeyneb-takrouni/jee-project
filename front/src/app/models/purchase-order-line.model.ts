export interface PurchaseOrderLine {
  id?: number;
  purchaseOrderId: number;
  productId: number;
  quantite: number;
  prixUnitaire: number;
}
