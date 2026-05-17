import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PurchaseOrderLine } from '../models/purchase-order-line.model';

@Injectable({
  providedIn: 'root'
})
export class PurchaseOrderLineService {
private apiUrl = '/api/purchase-order-lines';
  constructor(private http: HttpClient) {}

  getAllPurchaseOrderLines(): Observable<PurchaseOrderLine[]> {
    return this.http.get<PurchaseOrderLine[]>(this.apiUrl);
  }

  getPurchaseOrderLineById(id: number): Observable<PurchaseOrderLine> {
    return this.http.get<PurchaseOrderLine>(`${this.apiUrl}/${id}`);
  }

  createPurchaseOrderLine(line: PurchaseOrderLine): Observable<PurchaseOrderLine> {
    return this.http.post<PurchaseOrderLine>(this.apiUrl, line);
  }

  updatePurchaseOrderLine(id: number, line: PurchaseOrderLine): Observable<PurchaseOrderLine> {
    return this.http.put<PurchaseOrderLine>(`${this.apiUrl}/${id}`, line);
  }

  deletePurchaseOrderLine(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
