import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { catchError, throwError } from 'rxjs';
import { ResponseBody } from '../interfaces/all-clients-response';

@Injectable({
  providedIn: 'root'
})
export class ClientServiceService {

  constructor(private http: HttpClient) { }

  private apiUrl = environment.apiUrl + '/clients';

  getAllClients() {
    const token = localStorage.getItem('token');
    const headers = { 'Authorization': `Bearer ${token}` };

    return this.http.get<ResponseBody>(this.apiUrl, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  
  private handleError(error: HttpErrorResponse) {
    console.error('Backend returned error:', error);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
