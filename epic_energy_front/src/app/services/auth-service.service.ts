import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor(private cookieService: CookieService) { }

  setToken(token: string): void {
    this.cookieService.set('authToken', token, {
      secure: true,
      sameSite: 'Strict',
    });
  }

  getToken(): string | null {
    return this.cookieService.get('authToken');
  }

  clearToken(): void {
    this.cookieService.delete('authToken', '/', '', true, 'Strict');
    console.log('Token removed');
  }
}
