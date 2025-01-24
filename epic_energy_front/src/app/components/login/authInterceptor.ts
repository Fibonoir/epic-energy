import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { CookieService } from 'ngx-cookie-service';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private cookieService: CookieService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      const authToken = this.cookieService.get('authToken'); // Retrieve the token from cookies

      if (authToken) {
        // Clone the request and add the Authorization header
        const clonedReq = req.clone({
          headers: req.headers.set('Authorization', `Bearer ${authToken}`),
        });
        return next.handle(clonedReq);
      }

      // If no token, proceed with the original request
      return next.handle(req);
  }
}
