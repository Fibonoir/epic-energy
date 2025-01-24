import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { catchError, throwError } from 'rxjs';
import { ResponseBody } from '../interfaces/all-clients-response';

@Injectable({
  providedIn: 'root'
})
export class ClientServiceService {
  private apiUrl = environment.apiUrl + '/clients';

  constructor(private http: HttpClient) {}

  getAllClients() {
    return this.http.get<ResponseBody>(this.apiUrl)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    console.error('Backend returned error:', error);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
