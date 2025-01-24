import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { iInvoices } from '../interfaces/invoices';
import { environment } from '../../environments/environment.development';
import { catchError, map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InvoiceServiceService {
  constructor(private http: HttpClient) {}

  apiUrl = environment.apiUrl;
  getAllInvoices() {
    return this.http
      .get<{ content: iInvoices[] }>(this.apiUrl + '/invoices')
      .pipe(map((response) => response.content));
  }

  deleteInvoiceById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/invoices/${id}`);
  }
}
