import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PurchaseHistory } from '../models/purchase-history.model';
import { Supplier } from '../models/supplier.model';

@Injectable({
  providedIn: 'root'
})
export class PurchaseHistoryService {
  private apiUrl = 'http://localhost:8081/api/purchase-histories';

  constructor(private http: HttpClient) {}

  getAllPurchaseHistories(): Observable<PurchaseHistory[]> {
    return this.http.get<PurchaseHistory[]>(this.apiUrl);
  }

  getPurchaseHistoryById(id: number): Observable<PurchaseHistory> {
    return this.http.get<PurchaseHistory>(`${this.apiUrl}/${id}`);
  }

  createPurchaseHistory(history: PurchaseHistory): Observable<PurchaseHistory> {
    return this.http.post<PurchaseHistory>(this.apiUrl, history);
  }

  updatePurchaseHistory(id: number, history: PurchaseHistory): Observable<PurchaseHistory> {
    return this.http.put<PurchaseHistory>(`${this.apiUrl}/${id}`, history);
  }

  deletePurchaseHistory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  evaluateSupplier(supplierId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/evaluate-supplier/${supplierId}`);
  }

  compareOffers(productId: number): Observable<Supplier[]> {
    return this.http.get<Supplier[]>(`${this.apiUrl}/compare-offers/${productId}`);
  }
}
