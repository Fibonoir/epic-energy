import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private cookieService: CookieService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const token = this.cookieService.get('authToken'); // Check for token in cookies

    if (token) {
      // User is logged in
      return true;
    } else {
      // User is not logged in, redirect to login page
      this.router.navigate([''], {
        queryParams: { returnUrl: state.url }, // Optionally, pass the attempted URL
      });
      return false;
    }
  }
}
